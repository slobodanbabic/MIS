package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long studentId;
	private String ime;
	private String prezime;
	private String brIndexa;
	private float saldo;
	private boolean budzet;
	private String password;

	// bi-directional many-to-many association to Predmet
	@ManyToMany(mappedBy = "students")
	private List<Predmet> predmets;

	// bi-directional many-to-many association to Ispit
	@OneToMany(mappedBy = "student")
	private List<Ispit> ispiti;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	private List<Transakcija> transakcije;

	public Student() {
		predmets = new ArrayList<Predmet>();
		ispiti = new ArrayList<Ispit>();
		transakcije = new ArrayList<Transakcija>();
	}

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getBrIndexa() {
		return brIndexa;
	}

	public void setBrIndexa(String brIndexa) {
		this.brIndexa = brIndexa;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public boolean isBudzet() {
		return budzet;
	}

	public void setBudzet(boolean budzet) {
		this.budzet = budzet;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Predmet> getPredmets() {
		return predmets;
	}

	public void setPredmets(List<Predmet> predmets) {
		this.predmets = predmets;
	}

	public List<Ispit> getIspiti() {
		return ispiti;
	}

	public void setIspiti(List<Ispit> ispiti) {
		this.ispiti = ispiti;
	}

	public List<Transakcija> getTransakcije() {
		return transakcije;
	}

	public void setTransakcije(List<Transakcija> transakcije) {
		this.transakcije = transakcije;
	}

	public void dodajTransakciju(Transakcija t) {
		transakcije.add(t);
		t.setStudent(this);
	}

	@Override
	public String toString() {
		return ime + " " + prezime + " " + brIndexa;
	}

}
