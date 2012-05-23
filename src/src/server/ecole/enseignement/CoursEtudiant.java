package src.server.ecole.enseignement;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cours-etudiant")
public class CoursEtudiant implements Serializable{

	private static final long serialVersionUID = -8814391647994481140L;
	public static final String ID_FIELD_NAME = "id"; 
	public static final String COURS_FIELD_NAME = "cours"; 
	public static final String ETUDIANT_FIELD_NAME = "etudiant";
	public static final String NOTE_FIELD_NAME = "note"; 
	
	@DatabaseField(columnName=ID_FIELD_NAME , generatedId = true)
	private Integer id;
	
	@DatabaseField(columnName=COURS_FIELD_NAME , canBeNull = false, uniqueCombo = true, foreign = true, foreignAutoRefresh = true)
	private Cours cours;
	
	@DatabaseField(columnName=ETUDIANT_FIELD_NAME , canBeNull = false, uniqueCombo = true, foreign = true, foreignAutoRefresh = true)
	private Etudiant etudiant;
	
	@DatabaseField(columnName=NOTE_FIELD_NAME , canBeNull = true)
	private Integer note;
	
	public CoursEtudiant() {
		
	}
	
	public CoursEtudiant(Etudiant etudiant, Cours cours) {
		this.etudiant = etudiant;
		this.cours = cours;
	}

	public void setCours(Cours cours) {
		this.cours = cours;
	}
	
	public Cours getCours() {
		return cours;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Etudiant getEtudiant() {
		return etudiant;
	}
	
	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public Integer getNote() {
		return note;
	}

	public void setNote(Integer note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "CoursEtudiant [id=" + id + ", cours=" + cours + ", etudiant="
				+ etudiant + ", note=" + note + "]";
	}
	
	
}
