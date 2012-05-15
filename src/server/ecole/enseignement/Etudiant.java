package server.ecole.enseignement;

import java.io.Serializable;

public class Etudiant extends User{
	
	private static final long serialVersionUID = -360283760227367903L;

	public Etudiant(String nom, String pwd){
		super(nom, pwd);
		this.acces = TypeAcces.STUDENT_RESTRICT;
	}
}
