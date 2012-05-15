package server.ecole.enseignement;

import java.io.Serializable;

public class User implements Serializable{
	
	private static final long serialVersionUID = 4829006437935973762L;

	protected static enum TypeAcces {TOTAL, PROF_RESTRICT, STUDENT_RESTRICT, NONE};
	protected String nom;
	protected String pwd;
	protected TypeAcces acces = TypeAcces.NONE;	
	
	public User(String nom, String pwd) {
		this.nom = nom;
		this.pwd = pwd;
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
		return "User [nom=" + nom + "]";
	}
	
	
}
