package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Ispit {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private long ispitId;
	private String ispitniRok;
	private String datum;
	private int ocena;
	
	

	

	@ManyToOne
	@JoinColumn(name="STUDENT_ID")
	private Student student;
	
	
	@ManyToOne
	@JoinColumn(name="PREDMET_ID")
	private Predmet predmet;
	
	
	public Ispit() {		
	}


	public String getIspitniRok() {
		return ispitniRok;
	}


	public void setIspitniRok(String ispitniRok) {
		this.ispitniRok = ispitniRok;
	}


	public String getDatum() {
		return datum;
	}


	public void setDatum(String datum) {
		this.datum = datum;
	}


	public int getOcena() {
		return ocena;
	}


	public void setOcena(int ocena) {
		this.ocena = ocena;
	}


	
	public long getIspitId() {
		return ispitId;
	}


	public void setIspitId(long ispitId) {
		this.ispitId = ispitId;
	}



	public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}


	public Predmet getPredmet() {
		return predmet;
	}


	public void setPredmet(Predmet predmet) {
		this.predmet = predmet;
	}
	
	
	
}
