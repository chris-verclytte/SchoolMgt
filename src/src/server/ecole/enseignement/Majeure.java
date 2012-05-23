package src.server.ecole.enseignement;
import java.io.Serializable;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Majeure implements Serializable{

	private static final long serialVersionUID = 9126554273213388880L;
	public static final String LIBELLE_FIELD_NAME = "libelle"; 
	public static final String ETUDIANTS_FIELD_NAME = "etudiants"; 
	
	@DatabaseField(id = true)
	private String libelle;
	
	@ForeignCollectionField(eager = true)
	ForeignCollection<Etudiant> etudiantsInscrits;
	
	
	public Majeure(){
	}
	
	public Majeure(String libelle){
		this.libelle = libelle;
	}
	
	public Etudiant rechercherEtudiant(String etudiant){
		return null;
	}
	
	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public ForeignCollection<Etudiant> getEtudiantsInscrits() {
		return etudiantsInscrits;
	}

	public void setEtudiantsInscrits(ForeignCollection<Etudiant> etudiantsInscrits) {
		this.etudiantsInscrits = etudiantsInscrits;
	}

	// REDEFINITIONS
	public String toString(){
		return libelle;
	}
	
	public boolean equals(Majeure majeure){
		return (this.libelle == majeure.libelle) ? true : false;
	}
}
