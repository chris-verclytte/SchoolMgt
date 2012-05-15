package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket socket;
	private InetAddress adresseServeur;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;
	private String login;
	private String password;
	private String authentification;
	final int PORT_SERVER = 6789;
	
	public Client() {			
		try {
			log("Demande d'authentification.", "LOG");
			askForAuthInformation();
			establishLink();
		} catch (IOException e) {
			log("Erreur d'entrée dans les identifiants.", "ERR");
		}
		sendToServer("pouet");
		handleResponse();
		closeLink();
	}
	
	public void setAuthInformation(String login, String password) {
		this.login = login;
		this.password = password;
	}
	
	public boolean establishLink() {
		try {
			createAuthentificationToken();
			log("Recherche de l'hote localhost", "LOG");
			adresseServeur = InetAddress.getByName( "localhost" );
			log("Tentative de connexion à localhost sur le port "+PORT_SERVER, "LOG");
			socket = new Socket(adresseServeur, PORT_SERVER);			
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			inputStream = new ObjectInputStream(socket.getInputStream());	
		} catch (UnknownHostException e) {
				log("L'hote demande est inconnu. (" + e.getMessage() +")" , "ERR");
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
		String login, pass;
		System.out.println("Entrez votre login :");
		login = in.readLine();
		System.out.println("Entrez votre mot de passe :");
		pass = in.readLine();
		setAuthInformation(login, pass);
	}
	
	public void createAuthentificationToken() {
		authentification = login + " " + password; 
	}

	public boolean sendToServer(Object obj) {
		try {
			log("Envoi du token d'authentification: " + (String) authentification, "LOG");
			outputStream.writeObject(authentification);
			log("Tentative d'envoi de l'objet: " + (String) obj, "LOG");
			outputStream.writeObject(obj);
		} catch (IOException e) {
			System.out.println("[ERREUR] Problème d'envoi du message au server.");
			return false;
		}
		return true;
	}
	
	public boolean handleResponse(){
		try {
			String response = (String) inputStream.readObject();
			log("Le serveur a répondu:\n\t" + response, "LOG");
		} catch (ClassNotFoundException e) {
			log("Impossible d'interpréter la réponse du serveur. ("+e.getMessage()+")", "ERR");
		} catch (IOException e) {
			log("Le serveur n'a pas renvoyé de réponse. ("+e.getMessage()+")", "ERR");
		}
		return false;
	}
	
	public void sendToServerWithError() {
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Client();	
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
