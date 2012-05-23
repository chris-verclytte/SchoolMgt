package src.server.ecole.enseignement;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Etudiant extends Utilisateur{
	
	private static final long serialVersionUID = -360283760227367903L;
	public static final String MAJEURE_FIELD_NAME = "majeure"; 
	public static final String PROFESSEUR_FIELD_NAME = "tuteur"; 
	
	@DatabaseField(columnName=MAJEURE_FIELD_NAME, canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private Majeure majeure;
	
	@DatabaseField(columnName=PROFESSEUR_FIELD_NAME, canBeNull = false, foreign = true, foreignAutoRefresh = true)
	private Professeur tuteur;
	
	public Etudiant() {
		super();
	}
	
	public Etudiant(String nom, String pwd){
		super(nom, pwd);
		this.acces = TypeAcces.ETU_RESTRICT;
	}

	public Majeure getMajeure() {
		return majeure;
	}

	public void setMajeure(Majeure majeure) {
		this.majeure = majeure;
	}

	public Professeur getTuteur() {
		return tuteur;
	}

	public void setTuteur(Professeur tuteur) {
		this.tuteur = tuteur;
	}
}
