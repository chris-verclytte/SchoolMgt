package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class gestionConnection implements  Runnable {
	private Socket socket;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	String login = "";
	String pass = "";
	public gestionConnection(Socket socketClient) {
		socket = socketClient;
	}

	@Override
	public void run() {
		try {
			openLinks();
			log("Un client s'est connecte.", "LOG");
			
			String authentificationToken = (String) inputStream.readObject();
			log("L'utilisateur tente de s'authentifier avec le token " + authentificationToken, "LOG");
			
			authentifierUtilisateur(authentificationToken);
			String objetRecu = (String) inputStream.readObject();
			log("Le serveur a bien reçu l'objet: " + objetRecu, "LOG");
			
			
			// Gestion de sa demande
			//gererTypeDeRequete();
			
		} catch (IOException e) {
			log("Problème de communication entre le serveur et le client ("+e.getMessage()+")", "ERR");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			log("Erreur d'interprétation de classe: " + e.getMessage(),"ERR");
		}
	}

	public void openLinks() throws IOException {
		inputStream = new ObjectInputStream(socket.getInputStream());
		outputStream = new ObjectOutputStream(socket.getOutputStream());	
	}
	
	public boolean authentifierUtilisateur(String authentificationToken) {
		if(isValidUser(authentificationToken)){
			repToClient("Authentification acceptée.\nUtilisateur connecte : " + authentificationToken);
			log(authentificationToken +" vient de se connecter ", "LOG");
			return true;
		}
		else {
			repToClient("Authentification invalide.");
			log(authentificationToken +": Authentification rejetée. ", "ERR");
			return false;
		}
	}

	
	public boolean isValidUser(String authentificationToken) {
		boolean isValid = true;
		return isValid;
	}
	
	public void gererDemandeClient() {
		try {
			inputStream.readObject();
		} catch (IOException e) {
			log("Object Error: Le type d'objet doit etre string", "ERR");
			repToClientError("Object Error: Le type d'objet doit etre string");
		}
		catch (ClassNotFoundException e1) {
			log("Object Error: Le type d'objet doit etre string", "ERR");
			repToClientError("Object Error: Le type d'objet doit etre string");
		}
	}
	
	public void repToClient(Object obj) {
		try {
			outputStream.writeObject(obj);
		} catch (IOException e) {
			log("Impossible d'envoyer le message au client. (" + e.getMessage() + ")", "ERR");
		}
	}
	
	public void repToClientError(String message) {
		try {
			outputStream.writeObject("[ERROR]" + message);
		} catch (IOException e) {
			log("Impossible d'envoyer le message au client. (" + e.getMessage() + ")", "ERR");
		}
	}
	
	
	@Override
	protected void finalize() throws Throwable {
		socket.close();
		super.finalize();
	}
	
	public static void log(String msg, String flags) {
		if (flags.contains("LOG") || flags.isEmpty()) System.out.print("[LOG]");
		if (flags.contains("ERR")) System.out.print("[ERROR]");
		if (flags.contains("WARN")) System.out.print("[WARNING]");
		System.out.println(msg);
	}
	

}
