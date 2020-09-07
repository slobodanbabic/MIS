package crud;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import model.Ispit;
import model.Predmet;
import model.Student;
import model.Transakcija;
import utils.PersistenceUtil;

public class StudentCrud {

	public Student insertStudent(Student s) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			em.persist(s);
			em.flush();
			et.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			if (et != null) {
				et.rollback();
			}
		} finally {
			if (em != null && em.isOpen()) {
				em.close();
			}
		}
		return s;
	}

	public void poveziStudentaIPredmet(Student s, Predmet p) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			s = em.merge(s);
			p = em.merge(p);
			s.getPredmets().add(p);
			p.getStudents().add(s);
			em.merge(s);
			em.merge(p);
			em.flush();
			et.commit();
		} catch (Exception ex) {
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

	public void poveziStudentaIIspit(Student s, Ispit i) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			s = em.merge(s);
			i = em.merge(i);

			s.getIspiti().add(i);
			i.setStudent(s);

			em.merge(s);
			em.merge(i);
			em.flush();
			et.commit();
		} catch (Exception ex) {
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

	public Student findStudent(long id) {
		EntityManager em = PersistenceUtil.getEntityManager();
		Student p = em.find(Student.class, id);
		em.close();
		return p;
	}

	public Student getStudent(Student s) {
		EntityManager em = PersistenceUtil.getEntityManager();
		String upit = "select s from Student s where s.password=:password and s.brIndexa =:brIndexa";
		Query q = em.createQuery(upit);
		q.setParameter("password", s.getPassword());
		q.setParameter("brIndexa", s.getBrIndexa());
		Student stud = (Student) q.getSingleResult();
		em.close();
		return stud;
	}
	
	public Student getStudentBrIndexa(Student s) {
		EntityManager em = PersistenceUtil.getEntityManager();
		String upit = "select s from Student s where s.brIndexa =:brIndexa";
		Query q = em.createQuery(upit);
		q.setParameter("brIndexa", s.getBrIndexa());		
		Student stud = (Student) q.getSingleResult();
		em.close();
		return stud;
	}

	@SuppressWarnings("unchecked")
	public List<Ispit> polozeniIspiti(Student s) {
		EntityManager em = PersistenceUtil.getEntityManager();
		s = em.merge(s);
		Query upit = em.createQuery("select i from Student s join s.ispiti i where s.studentId =:id and i.ocena >= 6");
		upit.setParameter("id", s.getStudentId());
		List<Ispit> polozeniIspiti = upit.getResultList();
		em.close();
		return polozeniIspiti;
	}

	public List<Predmet> listPredmeti(Student s) {
		EntityManager em = PersistenceUtil.getEntityManager();
		s = em.merge(s);
		s.getPredmets().size();
		List<Predmet> predmeti = s.getPredmets();
		em.close();
		return predmeti;
	}

	public List<Predmet> listSlobodniPredmeti(Student s) {
		List<Predmet> predmeti = listPredmeti(s);
		List<Predmet> temp = new ArrayList<>();
		List<Ispit> polozeni = polozeniIspiti(s);

		List<Ispit> prijavljeni = getPrijavljeniIspiti(s);

		for (Ispit i : polozeni)
			temp.add(i.getPredmet());

		// brisem polozene
		predmeti.removeAll(temp);
		temp.clear();

		for (Ispit i : prijavljeni)
			temp.add(i.getPredmet());
		// brisem prijavljene
		predmeti.removeAll(temp);
		return predmeti;

	}

	public Student logovanje(String brIndexa, String password) {
		Student s = null;
		EntityManager em = PersistenceUtil.getEntityManager();
		String upit = "select s from Student s where s.brIndexa = :brIndexa and s.password = :password";
		TypedQuery<Student> q = em.createQuery(upit, Student.class);
		q.setParameter("brIndexa", brIndexa);
		q.setParameter("password", password);
		List<Student> temp = q.getResultList();
		if (!temp.isEmpty())
			s = temp.get(0);
		em.close();
		return s;
	}

	public List<Ispit> getPrijavljeniIspiti(Student s) {
		String today = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		EntityManager em = PersistenceUtil.getEntityManager();
		String upit = "select i from Ispit i where i.student.studentId =:id and i.ocena = 0 and i.datum =:datum";
		TypedQuery<Ispit> q = em.createQuery(upit, Ispit.class);
		q.setParameter("id", s.getStudentId());
		q.setParameter("datum", today);
		List<Ispit> prijavljeniIspiti = q.getResultList();
		em.close();
		return prijavljeniIspiti;
	}

	public void zaduziStudenta(Student s, Transakcija t) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		StudentCrud sc = new StudentCrud();
		s = sc.findStudent(s.getStudentId());	
		try {
			et = em.getTransaction();
			et.begin();				
			s = em.merge(s);
			t = em.merge(t);
			s.dodajTransakciju(t);
			float iznos = s.getSaldo() - t.getIznos();			
			s.setSaldo(iznos);
			
			em.merge(t);
			em.merge(s);

			em.flush();
			
			et.commit();
		} catch (Exception ex) {
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
	

	
	public List<Transakcija> listTransakcije(Student s) {		
		EntityManager em = PersistenceUtil.getEntityManager();
		s = em.merge(s);
		s.getTransakcije().size();
		List<Transakcija> temp = s.getTransakcije();		
		em.close();
		return temp;
	}
}
