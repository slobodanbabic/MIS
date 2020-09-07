package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


/**
 * The persistent class for the NASTAVNIK database table.
 * 
 */
@Entity
@NamedQuery(name="Nastavnik.findAll", query="SELECT n FROM Nastavnik n")
public class Nastavnik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="NASTAVNIK_ID")
	private long nastavnikId;
	private String ime;
	private String prezime;
	
	//bi-directional many-to-many association to Predmet
	@OneToMany(mappedBy="nastavnik",cascade=CascadeType.ALL)
	private List<Predmet> predmets;

	public Nastavnik(){
		predmets = new ArrayList<Predmet>();
	}
	

	public long getNastavnikId() {
		return nastavnikId;
	}



	public void setNastavnikId(long nastavnikId) {
		this.nastavnikId = nastavnikId;
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



	public List<Predmet> getPredmets() {
		return predmets;
	}



	public void setPredmets(List<Predmet> predmets) {
		this.predmets = predmets;
	}



	@Override
	public String toString() {
		return ime + " " + prezime ;
	}
	
	

}