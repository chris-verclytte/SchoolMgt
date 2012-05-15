package server.ecole.enseignement;

import java.io.Serializable;

public class Professeur extends User{
	
	private static final long serialVersionUID = 382025549428252835L;
	private String domaine;
	
	public Professeur(String nom, String domaine, String pwd){
		super(nom, pwd);
		this.domaine = domaine;
		this.acces = TypeAcces.PROF_RESTRICT;
	}

	public String getDomaine() {
		return domaine;
	}

	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}
}
