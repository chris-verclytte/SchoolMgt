package server.ecole;
import java.io.Serializable;
import java.util.*;

import server.ecole.enseignement.*;

public class Ecole implements Serializable{
	
	private static final long serialVersionUID = 4359739201152245925L;
	private String libelle;
	private Vector<Etudiant> etudiants;
	private Vector<Professeur> professeurs;
	
	public Vector<Etudiant> getEtudiantsInscrits() {
		return etudiants;
	}

	public void setEtudiantsInscrits(Vector<Etudiant> etudiantsInscrits) {
		this.etudiants = etudiantsInscrits;
	}

	public Vector<Professeur> getProfesseurs() {
		return professeurs;
	}

	public void setProfesseurs(Vector<Professeur> professeurs) {
		this.professeurs = professeurs;
	}

	public Ecole(String libelle) {
		this.libelle = libelle; 
		etudiants = new Vector<Etudiant>();
	}

	public void ajouterEtudiant(Etudiant etudiant) {
		if (!etudiants.contains(etudiant)) etudiants.addElement(etudiant);
	}
	
	public void supprimerEtudiant(Etudiant etudiant) {
		etudiants.removeElement(etudiant);
	}
	
	public void ajouterProfesseur(Professeur prof) {
		if (!professeurs.contains(prof)) professeurs.addElement(prof);
	}
	
	public void supprimerProfesseur(Professeur prof) {
		professeurs.removeElement(prof);
	}
	
	public Etudiant rechercherEtudiant(String etudiant){
		return ((etudiants.indexOf(etudiant) != -1) ? etudiants.elementAt(etudiants.indexOf(etudiant)) : null);//: return null;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public int getNbEtudiants() {
		return etudiants.size();
	}

	@Override
	public String toString() {
		return "Ecole [libelle=" + libelle + ", etudiants=" + etudiants
				+ ", professeurs=" + professeurs + "]";
	}
}
