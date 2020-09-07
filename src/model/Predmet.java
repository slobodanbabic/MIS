package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * The persistent class for the PREDMET database table.
 * 
 */
@Entity
@NamedQuery(name = "Predmet.findAll", query = "SELECT p FROM Predmet p")
public class Predmet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long predmetId;
	private String naziv;
	private float esp;
	private boolean prijavljen;

	// bi-directional many-to-many association to Student
	@ManyToMany
	@JoinTable(name = "SLUSA", joinColumns = { @JoinColumn(name = "PREDMET_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "STUDENT_ID") })
	private List<Student> students;

	@ManyToOne
	@JoinColumn(name = "NASTAVNIK_ID")
	private Nastavnik nastavnik;

	@OneToMany(mappedBy = "predmet")
	private List<Ispit> ispits;

	public Predmet() {
		students = new ArrayList<>();
		ispits = new ArrayList<>();
	}

	public long getPredmetId() {
		return predmetId;
	}

	public void setPredmetId(long predmetId) {
		this.predmetId = predmetId;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public float getEsp() {
		return esp;
	}

	public void setEsp(float esp) {
		this.esp = esp;
	}

	public boolean isPrijavljen() {
		return prijavljen;
	}

	public void setPrijavljen(boolean prijavljen) {
		this.prijavljen = prijavljen;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Nastavnik getNastavnik() {
		return nastavnik;
	}

	public void setNastavnik(Nastavnik nastavnik) {
		this.nastavnik = nastavnik;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (predmetId ^ (predmetId >>> 32));
		return result;
	}

	public List<Ispit> getIspits() {
		return ispits;
	}

	public void setIspits(List<Ispit> ispits) {
		this.ispits = ispits;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Predmet other = (Predmet) obj;
		if (predmetId != other.predmetId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return naziv;
	}
}