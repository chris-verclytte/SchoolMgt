package src.server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

import src.protocole.Token;
import src.protocole.TypeReponseServer;
import src.protocole.TypeRequeteServer;
import src.server.ecole.Ecole;
import src.server.ecole.enseignement.Cours;
import src.server.ecole.enseignement.CoursEtudiant;
import src.server.ecole.enseignement.CoursMajeure;
import src.server.ecole.enseignement.Etudiant;
import src.server.ecole.enseignement.Majeure;
import src.server.ecole.enseignement.Professeur;
import src.server.ecole.enseignement.Utilisateur;
import src.server.ecole.enseignement.Utilisateur.TypeAcces;

public class gestionConnection implements  Runnable {
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private Utilisateur utilisateurActuel = null;
	private Server server;
	
	public gestionConnection(Socket socketClient, Server server) {
		socket = socketClient;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			openLinks();
			log("Un client s'est connecte.", "LOG");
			gererRequeteClient();
		} catch (IOException e) {
			log("Probl�me de communication entre le serveur et le client ("+e.getMessage()+")", "ERR");
			e.printStackTrace();
		}
	}

	private void openLinks() throws IOException {
		inputStream = new ObjectInputStream(socket.getInputStream());
		outputStream = new ObjectOutputStream(socket.getOutputStream());	
	}
	
	private boolean authentifierUtilisateur(Token authentificationToken) {
		if(utilisateurValide(authentificationToken)){
			reponseClient(TypeReponseServer.AUTHENTIFIE);
			log(authentificationToken +" vient de se connecter ", "LOG");
			return true;
		}
		else {
			reponseClient(TypeReponseServer.NON_AUTHENTIFIE);
			log(authentificationToken +": Authentification rejet�e. ", "ERR");
			return false;
		}
	}

	
	private boolean utilisateurValide(Token authentificationToken) {
		Utilisateur utilisateur = getUtilisateur(authentificationToken.login);
		log("Utilisateur: "+utilisateur.getNom()+"/"+utilisateur.getPwd(),"LOG");
		log("Authentification Token: "+authentificationToken.login+"/"+authentificationToken.pwd,"LOG");
		if (utilisateur != null && authentificationToken.pwd.equals(utilisateur.getPwd()))
		{
			utilisateurActuel = utilisateur;
			return true;
		}
		return false;
	}
	
	private boolean gererRequeteClient() {
		boolean connexionAccepte = false;
		try {
			// Authentification Utilisateur
			Token authentificationToken = (Token) inputStream.readObject();
			log("L'utilisateur tente de s'authentifier avec le token " + authentificationToken, "LOG");
			connexionAccepte = authentifierUtilisateur(authentificationToken);
			if (!connexionAccepte) return false;
			
			// Gestion de l'objet de la demande
			TypeRequeteServer requeteClient = (TypeRequeteServer) inputStream.readObject();
			log("Le serveur a bien re�u l'objet: "+requeteClient+". Traitement en cours.", "LOG");
			genererReponseClient(requeteClient);
			return true;
		} catch (ClassCastException e) {
			log("Cast invalide. ("+e.getMessage()+")", "ERR");
			reponseClientErreur("L'objet envoy� est invalide.("+e.getMessage()+")");
		} catch (ClassNotFoundException e) {
			log("Objet envoy� invalide.("+e.getMessage()+")", "ERR");
			reponseClientErreur("L'objet envoy� est invalide.("+e.getMessage()+")");
		} catch (IOException e) {
			log("Probl�me de connexion avec le client. ("+e.getMessage()+")", "ERR");
			reponseClientErreur("Probl�me de connexion durant le traitement de la requ�te.("+e.getMessage()+")");
		}
		return false;
	}
	
	private void reponseClient(Object obj) {
		try {
			outputStream.writeObject(obj);
		} catch (IOException e) {
			log("Impossible d'envoyer le message au client. (" + e.getMessage() + ")", "ERR");
		}
	}

	private void reponseClientErreur(String explication) {
		try {
			outputStream.writeObject(TypeReponseServer.REQUETE_ERROR);
			outputStream.writeObject(explication);
		} catch (IOException e) {
			log("Impossible d'envoyer le message au client. (" + e.getMessage() + ")", "ERR");
		}
	}

	private void genererReponseClient(TypeRequeteServer requete) {
		switch(requete) {
		case GET_ECOLE: getEcole(); break;
		case UPDATE_PWD: updatePassword(); break;
		case UPDATE_MAJEURE: updateMajeure(); break;
		case UPDATE_COURS: break;
		case ADD_STUDENT: break;
		case ADD_COURS: reponseClientErreur("R�qu�te invalide. Se r�f�rer au protocole"); break;
		default: break;
		}
	}

	private void getEcole() {
		Ecole ecole = null;
		if (utilisateurActuel.getAcces() == TypeAcces.ETU_RESTRICT){
			ecole = getEcoleEtudiant();
		}
		else if (utilisateurActuel.getAcces() == TypeAcces.PROF_RESTRICT) {
			ecole = getEcoleProfesseur();
		}
		if (ecole != null) {
			reponseClient(TypeReponseServer.REQUETE_EXECUTE);
			reponseClient(ecole);
		}
		else reponseClientErreur("Erreur lors de la r�cup�ration des donn�es.");
	}
	
	
	private Ecole getEcoleProfesseur() {
		Ecole ecole = new Ecole();
		Vector<Professeur> professeurs = new Vector<Professeur>();
		Vector<Cours> cours = new Vector<Cours>();
		Vector<Etudiant> etudiants = new Vector<Etudiant>();
		Vector<CoursEtudiant> coursEtudiants = new Vector<CoursEtudiant>();
		
		professeurs.add((Professeur) utilisateurActuel);
		ecole.setProfesseurs(professeurs);
	
		try {
			cours = (Vector<Cours>) server.coursDao.queryBuilder().where()
				          .eq(Cours.PROFESSEUR_FIELD_NAME, utilisateurActuel.getNom()).query();
			ecole.setCours(cours);
			for (Cours cours1 : cours) {
				coursEtudiants.addAll((Vector<CoursEtudiant>) server.coursetudiantDao.queryBuilder().where()
						.eq(CoursEtudiant.COURS_FIELD_NAME, cours1.getLibelle()).query());
			}
		} catch (SQLException e) {
			log(e.getMessage(), "ERR");
			return null;
		}
		
		for (CoursEtudiant coursEtudiant : coursEtudiants) {
			if (!etudiants.contains(coursEtudiant.getEtudiant()))
					etudiants.add(coursEtudiant.getEtudiant());
		}
		
		ecole.setCoursEtudiants(coursEtudiants);
		ecole.setEtudiants(etudiants);
		return ecole;
	}

	private Ecole getEcoleEtudiant() {
		Ecole ecole = new Ecole();
		Vector<Professeur> professeurs = new Vector<Professeur>();
		Vector<Majeure> majeures = new Vector<Majeure>();
		Vector<Etudiant> etudiants = new Vector<Etudiant>();
		
		try {
			Etudiant etudiantActuel = (Etudiant) utilisateurActuel;
			etudiants.add(etudiantActuel);
			ecole.setEtudiants(etudiants); // On ajoute toujours qu'un seul �tudiant
			
			if (etudiantActuel.getMajeure() == null) {
					majeures = (Vector<Majeure>) server.majeureDao.queryForAll();
					ecole.setCoursMajeures((Vector<CoursMajeure>) server.coursmajeureDao.queryForAll());
			}
			else{
				majeures.add(etudiantActuel.getMajeure());
				ecole.setCoursMajeures((Vector<CoursMajeure>) server.coursmajeureDao.queryBuilder().where()
					.eq(CoursMajeure.MAJEURE_FIELD_NAME, etudiantActuel.getMajeure().getLibelle()).query());
			}
			
			ecole.setMajeures(majeures);
			ecole.setCours((Vector<Cours>) server.coursDao.queryForAll());
			ecole.setCoursEtudiants((Vector<CoursEtudiant>) server.coursetudiantDao.queryBuilder().where()
					.eq(CoursEtudiant.ETUDIANT_FIELD_NAME, etudiantActuel.getNom()).query());
		
			professeurs = (Vector<Professeur>) server.professeurDao.queryForAll();
			for(Professeur professeur : ecole.getProfesseurs()) {
				professeur.setPwd("");
			}
			ecole.setProfesseurs(professeurs);
		} catch (SQLException e) {
			log(e.getMessage(), "ERR");
		}
		
		return ecole;
	}
	/*
	private Vector<CoursEtudiant> getCoursEtudiant(Etudiant etudiant) {
		Vector<CoursEtudiant> coursEtudiants;
		try {
			coursEtudiants = (Vector<CoursEtudiant>) server.coursetudiantDao.queryBuilder().where()
					.eq(CoursEtudiant.ETUDIANT_FIELD_NAME, etudiant.getNom()).query();
		} catch (SQLException e) {
			log(e.getMessage(), "ERR");
			return null;
		}
		return coursEtudiants;
	}

	private Vector<CoursMajeure> getCoursMajeure(Majeure majeure) {
		Vector<CoursMajeure> coursMajeures;
		try {
			coursMajeures = (Vector<CoursMajeure>) server.coursmajeureDao.queryBuilder().where()
							.eq(CoursMajeure.MAJEURE_FIELD_NAME, majeure.getLibelle()).query();
		} catch (SQLException e) {
			log(e.getMessage(), "ERR");
			return null;
		}
		return coursMajeures;
	}
	*/
	
	private Utilisateur getUtilisateur(String nom) {
		try {
			Etudiant etudiant = server.etudiantDao.queryForId(nom);
			if (etudiant != null) return etudiant; 
			Professeur professeur = server.professeurDao.queryForId(nom);
			if (professeur != null) return professeur;
			//log(utilisateur.toString(), "LOG");
			//log("La requ�te pour l'utilisateur "+nom+" a renvoy� l'objet"+utilisateur,"LOG");
		} catch (Exception e) {
			log("Erreur : " + e.getMessage(), "ERR");
		}
		return null;
	}
	
	private boolean updatePassword() {
		Utilisateur utilisateurModifie;
		try {
			utilisateurModifie = (Utilisateur) inputStream.readObject();

			utilisateurActuel.setPwd(utilisateurModifie.getPwd());
			if (utilisateurActuel.getAcces() == TypeAcces.PROF_RESTRICT)
				server.professeurDao.update((Professeur) utilisateurActuel);
			else if (utilisateurActuel.getAcces() == TypeAcces.ETU_RESTRICT)
				server.etudiantDao.update((Etudiant) utilisateurActuel);	
		} catch (SQLException e) {
			log(e.getMessage(), "ERR");
			reponseClientErreur("Impossible de modifier l'utilisateur dans la BDD. ("+e.getMessage()+")");
			return false;
		}catch (ClassNotFoundException | IOException e) {
			log(e.getMessage(), "ERR");
			reponseClientErreur( e.getMessage());
			return false;
		}
		reponseClient(TypeReponseServer.REQUETE_EXECUTE);
		log("Mot de passe modifi� pour l'utilisateur "+utilisateurActuel.getNom(), "LOG");
		return true;
	}
	
	private boolean updateMajeure() {
		Majeure majeureModifiee;
		try {
			majeureModifiee = (Majeure) inputStream.readObject();
			server.majeureDao.update(majeureModifiee);
		}catch(SQLException e) {
			log("Erreur d'insertion dans la base de donn�es. ("+e.getMessage()+")", "ERR");
			reponseClientErreur("Erreur d'insertion dans la base de donn�es."+e.getMessage());
		}catch (ClassNotFoundException | IOException e1) {
			log("Erreur dans la transmision de l'objet ("+e1.getMessage()+")", "ERR");
			reponseClientErreur(e1.getMessage());
		}
		
		return true;
	}

	public static void log(String msg, String flags) {
		if (Server.muteLog) return;
		if (flags.contains("LOG") || flags.isEmpty()) System.out.print("[LOG]");
		if (flags.contains("ERR")) System.out.print("[ERROR]");
		if (flags.contains("WARN")) System.out.print("[WARNING]");
		System.out.println(msg);
	}

	@Override
	protected void finalize() throws Throwable {
		socket.close();
		super.finalize();
	}
	

}
