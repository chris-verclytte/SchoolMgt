package src.server.ecole.enseignement;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;

public class Utilisateur implements Serializable{
	
	private static final long serialVersionUID = 4829006437935973762L;

	public static enum TypeAcces {TOTAL, PROF_RESTRICT, ETU_RESTRICT, NONE};
	public static final String NOM_FIELD_NAME = "nom"; 
	public static final String PWD_FIELD_NAME = "pwd"; 
	public static final String ACCES_FIELD_NAME = "acces"; 
	
	@DatabaseField(columnName=NOM_FIELD_NAME, id=true)
	protected String nom;
	
	@DatabaseField(columnName=PWD_FIELD_NAME, canBeNull = false)
	protected String pwd;
	
	@DatabaseField(columnName=ACCES_FIELD_NAME)
	protected TypeAcces acces = TypeAcces.NONE;	
	
	public Utilisateur() {
		
	}
	
	public Utilisateur(String nom, String pwd) {
		this.nom = nom;
		this.pwd = pwd;
	}
	
	public TypeAcces getAcces() {
		return acces;
	}

	public void setAcces(TypeAcces acces) {
		this.acces = acces;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "Utilisateur [nom=" + nom + "]";
	}
	
	
}
