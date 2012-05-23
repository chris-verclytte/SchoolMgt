package src.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.table.TableUtils;

import src.server.ecole.enseignement.*;

public class Server {
	private String databaseUrl= "jdbc:hsqldb:file:./database/schoolmgt";
	//String databaseUrl = "jdbc:hsqldb:mem:.";
	private JdbcPooledConnectionSource connectionSource;
	
	
	private ServerSocket serverSocket;
	final int PORT = 6789;
	public static boolean muteLog = false;
	
	public Dao<Majeure, String> majeureDao;
	public Dao<Cours, String> coursDao;
	public Dao<Professeur, String> professeurDao;
	public Dao<Etudiant, String> etudiantDao;
	public Dao<CoursMajeure, Integer> coursmajeureDao;
	public Dao<CoursEtudiant, Integer> coursetudiantDao;
	
	public Server() throws Exception {
		
		initDatabase();
		
		initConnection();
		
		closeDatabase();
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
				
				Thread t = new Thread(new gestionConnection(socketClient, this));
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
			e.printStackTrace();
		}
	}
	
	public static void log(String msg, String flags) {
		if (Server.muteLog) return;
		if (flags.contains("LOG") || flags.isEmpty()) System.out.print("[LOG]");
		if (flags.contains("ERR")) System.out.print("[ERROR]");
		if (flags.contains("WARN")) System.out.print("[WARNING]");
		System.out.println(msg);
	}

	public boolean initDatabase() {
		try {
			// pooled connection source
			connectionSource = new JdbcPooledConnectionSource(databaseUrl +";create=true");
			//connectionSource.setUsername("schoolmgt");
			//connectionSource.setPassword("schoolmgt");
			// only keep the connections open for 5 minutes
			connectionSource.setMaxConnectionAgeMillis(5 * 60 * 1000);
			// for extra protection, enable the testing of connections
			// right before they are handed to the user
			connectionSource.setTestBeforeGet(true);
		} catch (SQLException e1) {
			log("Error retrieving the connection to SchoolMgt database. ("+e1.getMessage()+")", "ERR");
			return false;
		}
		log("Database initalized.", "LOG");

		try {
			majeureDao = DaoManager.createDao(connectionSource, Majeure.class);
			coursDao = DaoManager.createDao(connectionSource, Cours.class);
			professeurDao = DaoManager.createDao(connectionSource, Professeur.class);
			etudiantDao = DaoManager.createDao(connectionSource, Etudiant.class);
			coursmajeureDao = DaoManager.createDao(connectionSource, CoursMajeure.class);
			coursetudiantDao = DaoManager.createDao(connectionSource, CoursEtudiant.class);
		}catch (SQLException e) {
			log("Error initializing ORM objects: (" + e.getMessage() + ")","ERR");
			return false;
		}
		log("DAO Model loaded.", "LOG");
		
		try {
			TableUtils.createTableIfNotExists(connectionSource, Majeure.class);
			TableUtils.createTableIfNotExists(connectionSource, Cours.class);
			TableUtils.createTableIfNotExists(connectionSource, Professeur.class);
			TableUtils.createTableIfNotExists(connectionSource, Etudiant.class);
			TableUtils.createTableIfNotExists(connectionSource, CoursMajeure.class);
			TableUtils.createTableIfNotExists(connectionSource, CoursEtudiant.class);
		} catch (SQLException e) {
			log("Error creating tables: (" + e.getMessage() + ")","ERR");
			e.printStackTrace();
			return false;
		}
		log("Tables created.", "LOG");
			
		return true;
	}
	
	public boolean closeDatabase() {
		try {
			connectionSource.close();		
		} catch (SQLException e) {
			log("Error closing the database. ("+e.getMessage()+")", "WARN");
			return false;
		}
		log("Database closed.", "LOG");
		return true;
	}
	
	public String getDatabaseUrl() {
		return databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}

	public JdbcPooledConnectionSource getConnectionSource() {
		return connectionSource;
	}

	public void setConnectionSource(JdbcPooledConnectionSource connectionSource) {
		this.connectionSource = connectionSource;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public Dao<Majeure, String> getMajeureDao() {
		return majeureDao;
	}

	public void setMajeureDao(Dao<Majeure, String> majeureDao) {
		this.majeureDao = majeureDao;
	}

	public Dao<Cours, String> getCoursDao() {
		return coursDao;
	}

	public void setCoursDao(Dao<Cours, String> coursDao) {
		this.coursDao = coursDao;
	}

	public Dao<Professeur, String> getProfesseurDao() {
		return professeurDao;
	}

	public void setProfesseurDao(Dao<Professeur, String> professeurDao) {
		this.professeurDao = professeurDao;
	}

	public Dao<Etudiant, String> getEtudiantDao() {
		return etudiantDao;
	}

	public void setEtudiantDao(Dao<Etudiant, String> etudiantDao) {
		this.etudiantDao = etudiantDao;
	}

	public Dao<CoursMajeure, Integer> getCoursmajeureDao() {
		return coursmajeureDao;
	}

	public void setCoursmajeureDao(Dao<CoursMajeure, Integer> coursmajeureDao) {
		this.coursmajeureDao = coursmajeureDao;
	}

	public Dao<CoursEtudiant, Integer> getCoursetudiantDao() {
		return coursetudiantDao;
	}

	public void setCoursetudiantDao(Dao<CoursEtudiant, Integer> coursetudiantDao) {
		this.coursetudiantDao = coursetudiantDao;
	}

	@Override
	protected void finalize() throws Throwable {
		serverSocket.close();
		super.finalize();
	}

}
