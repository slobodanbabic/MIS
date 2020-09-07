package crud;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.Nastavnik;
import model.Predmet;
import utils.PersistenceUtil;

public class NastavnikCrud {

	public void insertNastavnik(Nastavnik n) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			em.persist(n);
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

	public void poveziNastavnikaIPredmet(Nastavnik n, Predmet p) {	
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();			
			et.begin();
			
			n = em.merge(n);
			p = em.merge(p);

			n.getPredmets().add(p);
			p.setNastavnik(n);

			em.merge(n);
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

	public Nastavnik getNastavnik(Nastavnik n){
		EntityManager em = PersistenceUtil.getEntityManager();
		String upit = "select n from Nastavnik n where n.ime=:x and n.prezime=:y";
		Query q = em.createQuery(upit);
		q.setParameter("x", n.getIme());
		q.setParameter("y", n.getPrezime());
		Nastavnik nast = (Nastavnik)q.getSingleResult();
		em.close();
		return nast;
	}

	public List<Nastavnik> listNastavnici() {
		EntityManager em = PersistenceUtil.getEntityManager();
		List<Nastavnik> nastavnici = em.createNamedQuery("Nastavnik.findAll", Nastavnik.class).getResultList();
		em.close();
		return nastavnici;
	}

}
