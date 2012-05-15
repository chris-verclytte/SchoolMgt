package server.ecole.enseignement;
import java.io.Serializable;
import java.util.*;

public class Cours implements Serializable{

	private static final long serialVersionUID = 1229597393117754662L;
	private String libelle;
	private int volumeHeure;
	private Professeur professeurAssigne;
	private Vector<Etudiant> etudiantsInscrits;
	
	public Cours(String libelle, int volumeHeure) {
		this.libelle = libelle;
		this.volumeHeure = volumeHeure;
		etudiantsInscrits = new Vector<Etudiant>();
	}
	
	public void assignerProfesseur(Professeur professeur){
		this.professeurAssigne = professeur;
	}
	
	public void ajouterEtudiant(Etudiant etudiant) {
		if (!etudiantsInscrits.contains(etudiant)) etudiantsInscrits.addElement(etudiant);
	}
	
	public void supprimerEtudiant(Etudiant etudiant) {
		etudiantsInscrits.removeElement(etudiant);
	}
	
	// GETTERS & SETTERS
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
