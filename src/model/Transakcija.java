package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transakcija {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long transakcijaId;
	private String opis;
	private float iznos;
	private String datum;

	@ManyToOne
	private Student student;

	public long getTransakcijaId() {
		return transakcijaId;
	}

	public void setTransakcijaId(long transakcijaId) {
		this.transakcijaId = transakcijaId;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public float getIznos() {
		return iznos;
	}

	public void setIznos(float iznos) {
		this.iznos = iznos;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

}
