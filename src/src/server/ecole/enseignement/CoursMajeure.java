package src.server.ecole.enseignement;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "cours-majeure")
public class CoursMajeure implements Serializable{

	private static final long serialVersionUID = 2601826377949811510L;
	public static final String ID_FIELD_NAME = "id"; 
	public static final String COURS_FIELD_NAME = "cours"; 
	public static final String MAJEURE_FIELD_NAME = "majeure"; 
	
	@DatabaseField(columnName=ID_FIELD_NAME, generatedId = true)
	private Integer id;
	
	@DatabaseField(columnName=COURS_FIELD_NAME, canBeNull = false, uniqueCombo = true, foreign = true, foreignAutoRefresh = true)
	private Cours cours;
	
	@DatabaseField(columnName=MAJEURE_FIELD_NAME, canBeNull = false, uniqueCombo = true, foreign = true, foreignAutoRefresh = true)
	private Majeure majeure;
	
	public CoursMajeure() {
		
	}
	
	public CoursMajeure(Cours cours, Majeure majeure) {
		this.cours = cours;
		this.majeure = majeure;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cours getCours() {
		return cours;
	}

	public void setCours(Cours cours) {
		this.cours = cours;
	}

	public Majeure getMajeure() {
		return majeure;
	}

	public void setMajeure(Majeure majeure) {
		this.majeure = majeure;
	}

	@Override
	public String toString() {
		return "CoursMajeure [id=" + id + ", cours=" + cours + ", majeure="
				+ majeure + "]";
	}
}
