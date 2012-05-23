package src.server.ecole;
import java.io.Serializable;
import java.util.Vector;

import src.server.ecole.enseignement.Cours;
import src.server.ecole.enseignement.CoursEtudiant;
import src.server.ecole.enseignement.CoursMajeure;
import src.server.ecole.enseignement.Etudiant;
import src.server.ecole.enseignement.Majeure;
import src.server.ecole.enseignement.Professeur;

public class Ecole implements Serializable{
	
	private static final long serialVersionUID = 4359739201152245925L;
	private String libelle;
	private Vector<Etudiant> etudiants;
	private Vector<Professeur> professeurs;
	private Vector<Majeure> majeures;
	private Vector<Cours> cours;
	private Vector<CoursEtudiant> coursEtudiants;
	private Vector<CoursMajeure> coursMajeures;
	
	public Ecole() {
	}
	
	public Ecole(String libelle) {
		this.libelle = libelle; 
		etudiants = new Vector<Etudiant>();
	}
	
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

	public Vector<Etudiant> getEtudiants() {
		return etudiants;
	}

	public void setEtudiants(Vector<Etudiant> etudiants) {
		this.etudiants = etudiants;
	}

	public Vector<Majeure> getMajeures() {
		return majeures;
	}

	public void setMajeures(Vector<Majeure> majeures) {
		this.majeures = majeures;
	}

	public Vector<Cours> getCours() {
		return cours;
	}

	public void setCours(Vector<Cours> cours) {
		this.cours = cours;
	}

	public Vector<CoursEtudiant> getCoursEtudiants() {
		return coursEtudiants;
	}

	public void setCoursEtudiants(Vector<CoursEtudiant> coursEtudiants) {
		this.coursEtudiants = coursEtudiants;
	}

	public Vector<CoursMajeure> getCoursMajeures() {
		return coursMajeures;
	}

	public void setCoursMajeures(Vector<CoursMajeure> coursMajeures) {
		this.coursMajeures = coursMajeures;
	}

	@Override
	public String toString() {
		return "Ecole [libelle=" + libelle + ", etudiants=" + etudiants
				+ ", professeurs=" + professeurs + "]";
	}
}
