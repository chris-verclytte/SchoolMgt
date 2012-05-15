package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import server.ecole.*;
import server.ecole.enseignement.*;

public class Server {
	private ServerSocket serverSocket;
	final int PORT = 6789;
	
	private Ecole ecole;
	private Vector<Majeure> majeures;
	private Vector<Cours> cours;
	private Vector<Etudiant> etudiants;
	private Vector<Professeur> professeurs;
	
	
	public Server() throws Exception {
		
		ecole = new Ecole("efrei");
		Etudiant etu1 = new Etudiant("Luc", "pouet");
		ecole.ajouterEtudiant(etu1);
		log(ecole.toString(), "LOG");
		initConnection();

	}
	
	public void initConnection() {
		try {
			serverSocket = new ServerSocket(PORT);
			log("Serveur démarré sur le port " + PORT, "LOG");
			while (true){
				log("En attente d'une connexion client.", "LOG");
				Socket socketClient = serverSocket.accept();
				log("Client connecté.", "LOG");
				
				log("Affichage des objets créés", "[LOG]");
				
				Thread t = new Thread(new gestionConnection(socketClient));
				t.start();
			} 
		} catch (IOException e) {
			log("Connexion avec le client impossible. (" + e.getMessage() + ")", "ERR");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {	
		try {
			new Server();
		} catch (Exception e) {
			log("Le serveur ne peut démarrer. Message du serveur : " + e.getMessage(), "ERR");
		}
	}
	
	public static void log(String msg, String flags) {
		if (flags.contains("LOG") || flags.isEmpty()) System.out.print("[LOG]");
		if (flags.contains("ERR")) System.out.print("[ERROR]");
		if (flags.contains("WARN")) System.out.print("[WARNING]");
		System.out.println(msg);
	}

	@Override
	protected void finalize() throws Throwable {
		serverSocket.close();
		super.finalize();
	}

}
