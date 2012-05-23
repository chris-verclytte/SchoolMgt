package src.server.ecole.enseignement;
import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class Cours implements Serializable{

	private static final long serialVersionUID = 1229597393117754662L;
	public static final String LIBELLE_FIELD_NAME = "libelle"; 
	public static final String VOLUME_H_FIELD_NAME = "volumeHeure"; 
	public static final String PROFESSEUR_FIELD_NAME = "professeurAssigne"; 
 
	@DatabaseField(columnName=LIBELLE_FIELD_NAME, id = true)
	private String libelle;
	
	@DatabaseField(columnName=VOLUME_H_FIELD_NAME, canBeNull = true)
	private int volumeHeure;
	
	@DatabaseField(columnName=PROFESSEUR_FIELD_NAME, canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private Professeur professeurAssigne;
	
	public Cours() {
	}
	
	public Cours(String libelle, int volumeHeure) {
		this.libelle = libelle;
		this.volumeHeure = volumeHeure;
	}

	
	// GETTERS & SETTERS
	
	public Professeur getProfesseurAssigne() {
		return professeurAssigne;
	}

	public void setProfesseurAssigne(Professeur professeurAssigne) {
		this.professeurAssigne = professeurAssigne;
	}
	
	public void setVolumeHeure(int volumeHeure) {
		this.volumeHeure = volumeHeure;
	}
	
	public int getVolumeHeure() {
		return volumeHeure;
	}
	
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	public String getLibelle() {
		return libelle;
	}
	
	
	// REDEFINITIONS
	public String toString() {
		return libelle + ": " + volumeHeure + "h. Professeur assigné: " + professeurAssigne;
	}
	
	public boolean equals(Cours cours) {
		return (this.libelle == cours.getLibelle()) ? true : false;
	}
}
