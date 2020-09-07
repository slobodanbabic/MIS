package utils;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.h2.tools.Server;

import crud.IspitCrud;
import crud.NastavnikCrud;
import crud.PredmetCrud;
import crud.StudentCrud;
import gui.GlavniProzor;
import model.Ispit;
import model.Nastavnik;
import model.Predmet;
import model.Student;
import model.Transakcija;

public class Podaci {

	public static void obrisiSveizBaze() {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			em.createQuery("delete from Ispit").executeUpdate();
			em.createQuery("delete from Predmet").executeUpdate();
			em.createQuery("delete from Nastavnik").executeUpdate();
			em.createQuery("delete from Transakcija").executeUpdate();
			em.createQuery("delete from Student").executeUpdate();
			em.flush();
			et.commit();
		} catch (Exception ex) { // RuntimeException
			ex.printStackTrace();
			if (et != null) {
				et.rollback();
			}
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
	}

	public static void restart(Student s) {
		obrisiSveizBaze();
		inicijalizacija();
		StudentCrud sc = new StudentCrud();
		s = sc.getStudent(s);
		new GlavniProzor(s);
	}

	public static void inicijalizacija() {
		NastavnikCrud nc = new NastavnikCrud();
		PredmetCrud pc = new PredmetCrud();
		StudentCrud sc = new StudentCrud();
		IspitCrud ic = new IspitCrud();

		int brNast = nc.listNastavnici().size();
		if (brNast < 3) {
			Student s1 = new Student();
			Student s2 = new Student();
			Student s3 = new Student();

			s1.setIme("Jovan");
			s1.setPrezime("Jovanovic");
			s1.setBrIndexa("122/15");
			Transakcija t1 = new Transakcija();
			t1.setIznos(1000);
			t1.setOpis("Uplata");
			t1.setDatum("12-08-2020");
			s1.dodajTransakciju(t1);
			s1.setSaldo(1000);
			s1.setBudzet(false);
			s1.setPassword("jova");
			s1 = sc.insertStudent(s1);

			s2.setIme("Misa");
			s2.setPrezime("Miskovic");
			s2.setBrIndexa("341/15");
			Transakcija t2 = new Transakcija();
			t2.setIznos(1100);
			t2.setOpis("Uplata");
			t1.setDatum("12-08-2020");
			s2.dodajTransakciju(t2);
			s2.setSaldo(1100);
			s2.setBudzet(true);
			s2.setPassword("misa");
			s2 = sc.insertStudent(s2);

			s3.setIme("Ognjen");
			s3.setPrezime("Ognjenovic");
			s3.setBrIndexa("63/15");
			Transakcija t3 = new Transakcija();
			t3.setOpis("Uplata");
			t1.setDatum("12-08-2020");
			t3.setIznos(1500);
			s3.setSaldo(1500);
			s3.dodajTransakciju(t3);
			s3.setBudzet(true);
			s3.setPassword("ogi");
			sc.insertStudent(s3);

			Nastavnik n1 = new Nastavnik();
			n1.setIme("Marko");
			n1.setPrezime("Markovic");
			nc.insertNastavnik(n1);

			Nastavnik n2 = new Nastavnik();
			n2.setIme("Djordje");
			n2.setPrezime("Djordjevic");
			nc.insertNastavnik(n2);

			Nastavnik n3 = new Nastavnik();
			n3.setIme("Mirko");
			n3.setPrezime("Mirkovic");
			nc.insertNastavnik(n3);

			Predmet p1 = new Predmet();
			p1.setNaziv("Baze podataka 1");
			p1.setEsp(7);
			pc.insertPredmet(p1);

			Predmet p2 = new Predmet();
			p2.setNaziv("Informacioni sistemi 1");
			p2.setEsp(7);
			pc.insertPredmet(p2);

			Predmet p3 = new Predmet();
			p3.setNaziv("Uvod u programiranje");
			p3.setEsp(8);
			pc.insertPredmet(p3);

			Predmet p4 = new Predmet();
			p4.setNaziv("Modeliranje informacionih sistema");
			p4.setEsp(8);
			pc.insertPredmet(p4);

			Predmet p5 = new Predmet();
			p5.setNaziv("Vestacka inteligencija 1");
			p5.setEsp(7);
			pc.insertPredmet(p5);

			Predmet p6 = new Predmet();
			p6.setNaziv("Vestacka inteligencija 2");
			p6.setEsp(7);
			pc.insertPredmet(p6);

			Predmet p7 = new Predmet();
			p7.setNaziv("Baze podataka 2");
			p7.setEsp(7);
			pc.insertPredmet(p7);

			n1 = nc.getNastavnik(n1);
			n2 = nc.getNastavnik(n2);
			n3 = nc.getNastavnik(n3);

			p1 = pc.getPredmet(p1);
			p2 = pc.getPredmet(p2);
			p3 = pc.getPredmet(p3);
			p4 = pc.getPredmet(p4);
			p5 = pc.getPredmet(p5);
			p6 = pc.getPredmet(p6);
			p7 = pc.getPredmet(p7);

			s1 = sc.getStudentBrIndexa(s1);
			s2 = sc.getStudentBrIndexa(s2);
			s3 = sc.getStudentBrIndexa(s3);

			sc.poveziStudentaIPredmet(s1, p1);
			sc.poveziStudentaIPredmet(s1, p2);
			sc.poveziStudentaIPredmet(s1, p3);
			sc.poveziStudentaIPredmet(s1, p4);
			sc.poveziStudentaIPredmet(s1, p5);
			sc.poveziStudentaIPredmet(s1, p6);
			sc.poveziStudentaIPredmet(s1, p7);
			sc.poveziStudentaIPredmet(s2, p1);
			sc.poveziStudentaIPredmet(s2, p2);
			sc.poveziStudentaIPredmet(s2, p3);
			sc.poveziStudentaIPredmet(s2, p4);
			sc.poveziStudentaIPredmet(s2, p5);
			sc.poveziStudentaIPredmet(s2, p6);
			sc.poveziStudentaIPredmet(s2, p7);
			sc.poveziStudentaIPredmet(s3, p3);
			sc.poveziStudentaIPredmet(s3, p1);
			sc.poveziStudentaIPredmet(s3, p2);

			nc.poveziNastavnikaIPredmet(n1, p1);
			nc.poveziNastavnikaIPredmet(n1, p2);
			nc.poveziNastavnikaIPredmet(n1, p3);
			nc.poveziNastavnikaIPredmet(n2, p4);
			nc.poveziNastavnikaIPredmet(n2, p5);
			nc.poveziNastavnikaIPredmet(n3, p6);
			nc.poveziNastavnikaIPredmet(n3, p7);

			Ispit i1 = new Ispit();
			i1.setPredmet(p1);
			i1.setStudent(s1);
			i1.setIspitniRok("Julski ispitni rok");
			i1.setDatum("28-7-2020");
			i1.setOcena(10);
			ic.insertIspit(i1);

			Ispit i2 = new Ispit();
			i2.setPredmet(p3);
			i2.setStudent(s2);
			i2.setIspitniRok("Julski ispitni rok");
			i2.setDatum("25-7-2020");
			i2.setOcena(9);
			ic.insertIspit(i2);

		}

	}

	public static void pokretanjeH2Servera() {		      
		try {			
			Server.createWebServer().start();
			Class.forName("org.h2.Driver");			
			Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
			System.out.println(
					"Connection Established: " + conn.getMetaData().getDatabaseProductName() + "/" + conn.getCatalog());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Podaci.obrisiSveizBaze();
		System.exit(0);
	}
}
