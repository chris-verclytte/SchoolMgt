package src.server.ecole.enseignement;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class Professeur extends Utilisateur{
	
	
	private static final long serialVersionUID = 382025549428252835L;
	
    @ForeignCollectionField(eager = true)
    ForeignCollection<Etudiant> etudiantsSuivis;
    
    @ForeignCollectionField(eager = true)
    ForeignCollection<Cours> coursEnseignees;
	
	public Professeur() {
		super();
	}
	
	public Professeur(String nom, String pwd){
		super(nom, pwd);
		this.acces = TypeAcces.PROF_RESTRICT;
	}

	public ForeignCollection<Etudiant> getEtudiantsSuivis() {
		return etudiantsSuivis;
	}

	public void setEtudiantsSuivis(ForeignCollection<Etudiant> etudiantsSuivis) {
		this.etudiantsSuivis = etudiantsSuivis;
	}

	public ForeignCollection<Cours> getCoursEnseignees() {
		return coursEnseignees;
	}

	public void setCoursEnseignees(ForeignCollection<Cours> coursEnseignees) {
		this.coursEnseignees = coursEnseignees;
	}
}
