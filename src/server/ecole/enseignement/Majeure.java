package server.ecole.enseignement;
import java.io.Serializable;
import java.util.*;

public class Majeure implements Serializable{

	private static final long serialVersionUID = 9126554273213388880L;
	private String libelle;
	private Vector<Cours> majeureCours;
	private Vector<Etudiant> etudiantsInscrits;
	
	public Majeure(String libelle){
		this.libelle = libelle;
		majeureCours = new Vector<Cours>();
		etudiantsInscrits = new Vector<Etudiant>();
	}
	
	public void ajouterCours(Cours cours){
		if (!majeureCours.contains(cours)) majeureCours.addElement(cours);
	}
	
	public void supprimerCours(Cours cours){
		majeureCours.removeElement(cours);
	}
	
	public void ajouterEtudiant(Etudiant etudiant) {
		if (!etudiantsInscrits.contains(etudiant)) etudiantsInscrits.addElement(etudiant);
	}
	
	public void supprimerEtudiant(Etudiant etudiant) {
		etudiantsInscrits.removeElement(etudiant);
	}
	
	public Cours rechercherCours(String libelle){
		return ((majeureCours.indexOf(libelle) != -1) ? majeureCours.elementAt(majeureCours.indexOf(libelle)) : null);//: return null;
	}
	
	public Etudiant rechercherEtudiant(String etudiant){
		return ((etudiantsInscrits.indexOf(etudiant) != -1) ? etudiantsInscrits.elementAt(etudiantsInscrits.indexOf(etudiant)) : null);//: return null;
	}
	
	// REDEFINITIONS
	public String toString(){
		return libelle;
	}
	
	public boolean equals(Majeure majeure){
		return (this.libelle == majeure.libelle) ? true : false;
	}
}
