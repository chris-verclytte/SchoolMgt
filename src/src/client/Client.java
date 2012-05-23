package src.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import src.protocole.Token;
import src.protocole.TypeReponseServer;
import src.protocole.TypeRequeteServer;
import src.server.ecole.Ecole;
import src.server.ecole.enseignement.*;

public class Client {
	private Socket socket;
	private InetAddress adresseServeur;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private String login;
	private String pwd;
	private Token authentification = new Token();
	private int portServer = 6789;
	
	public Client() {			
		try {
			log("Demande d'authentification.", "LOG");
			askForAuthInformation();
			establishLink();
		} catch (IOException e) {
			log("Erreur d'entrée dans les identifiants.", "ERR");
		}

		closeLink();
	}
	
	public void setAuthInformation(String login, String password) {
		this.login = login;
		this.pwd = password;
	}
	
	public boolean isAuthInformation() {
		return (this.login != null && this.pwd != null)?true:false;
	}
	
	public boolean establishLink() {
		try {
			createAuthentificationToken();
			log("Recherche de l'hote localhost", "LOG");
			adresseServeur = InetAddress.getByName( "localhost" );
			log("Tentative de connexion à localhost sur le port "+portServer, "LOG");
			socket = new Socket(adresseServeur, portServer);			
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());	
		} catch (UnknownHostException e) {
				log("L'hote demandé est inconnu. (" + e.getMessage() +")" , "ERR");
				return false;
		} catch (IOException e1) {
			log(e1.getMessage(), "ERR");
			return false;
		}
		return true;
	}
	
	public void closeLink() {
		try {			
			inputStream.close();
			outputStream.close();	
			socket.close();
		} catch (IOException e) {
			log("Erreur durant la fermeture de la connexion.", "ERR");
		}
		
	}

	public void askForAuthInformation() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		if (isAuthInformation())
		{
			String login, pass;
			System.out.println("Entrez votre login :");
			login = in.readLine();
			System.out.println("Entrez votre mot de passe :");
			pass = in.readLine();
			setAuthInformation(login, pass);
		}
	}
	
	public void createAuthentificationToken() {
		authentification.login = login;
		authentification.pwd = pwd; 
	}
	
	public boolean updatePassword(String newPwd){
		Utilisateur utilisateur = new Utilisateur(login, newPwd);
		sendToServer(TypeRequeteServer.UPDATE_PWD, utilisateur);
		gererReponseServer(TypeRequeteServer.UPDATE_PWD);
		return true;
	}

	public boolean sendToServer(TypeRequeteServer requete, Object obj) {
		try {
			log("Envoi du token d'authentification: " + authentification, "LOG");
			outputStream.writeObject(authentification);
			log("Envoi de la requete:"+requete, "LOG");
			outputStream.writeObject(requete);
			if (obj != null) {
				log("Tentative d'envoi de l'objet: " + obj, "LOG");
				outputStream.writeObject(obj);
			}
		} catch (IOException e) {
			System.out.println("[ERREUR] Problème d'envoi du message au server.");
			return false;
		}
		return true;
	}
	
	public boolean gererReponseServer(TypeRequeteServer requete){
		try {
			TypeReponseServer reponse = (TypeReponseServer) inputStream.readObject();
			switch(reponse) {
			case AUTHENTIFIE: 
				log(reponse+": Utilisateur autorisée.", "LOG");
				break;
			case NON_AUTHENTIFIE: 
				log(reponse+": Utilisateur non autorisée.","ERR");
				break;
			case REQUETE_EXECUTE:
				gererRequeteExecutee(requete);
				log(reponse+": La requête a été traitée par le serveur.","LOG");
				break;
			case REQUETE_ERROR: 
				log(reponse+ ": "+(String) inputStream.readObject(),"ERR");
				break;
			default:
				log(reponse+": La requête renvoyée par le serveur est invalide.","ERR");
				break;
			}
			return true;
		} catch (ClassNotFoundException e) {
			log("Impossible d'interpréter la réponse du serveur. ("+e.getMessage()+")", "ERR");
		} catch (IOException e) {
			log("Le serveur n'a pas renvoyé de réponse. ("+e.getMessage()+")", "ERR");
		}
		return false;
	}
	
	public void gererRequeteExecutee(TypeRequeteServer requete) {
		switch(requete) {
		case GET_ECOLE: 
			gererRetourGetEcole(); 
			break;
		case UPDATE_PWD: case UPDATE_MAJEURE: case UPDATE_COURS:
		case ADD_STUDENT: case ADD_COURS:
			log(requete+": Requete effectuée correctement.","LOG");
			break;
		default:
			break;
		}
	}
	
	public boolean gererRetourGetEcole() {
		try {
			Ecole ecole = (Ecole) inputStream.readObject();
			log("Objet Ecole reçu: " + ecole, "LOG");
		} catch (ClassNotFoundException | IOException e) {
			log(e.getMessage(), "ERR");
			return false;
		}
		return true;		
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Client();	
	}
	
	public InetAddress getAdresseServeur() {
		return adresseServeur;
	}

	public void setAdresseServeur(InetAddress adresseServeur) {
		this.adresseServeur = adresseServeur;
	}

	public int getPortServer() {
		return portServer;
	}

	public void setPortServer(int portServer) {
		this.portServer = portServer;
	}

	public static void log(String msg, String flags) {
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
