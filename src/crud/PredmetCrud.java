package crud;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import model.Predmet;
import utils.PersistenceUtil;

public class PredmetCrud {

	public void insertPredmet(Predmet p) {
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;
		try {
			et = em.getTransaction();
			et.begin();
			p = em.merge(p);
			em.persist(p);
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
	
	public Predmet getPredmet(Predmet p){
		EntityManager em = PersistenceUtil.getEntityManager();
		String upit = "select p from Predmet p where p.naziv = :naziv";
		Query q = em.createQuery(upit);
		q.setParameter("naziv",p.getNaziv());
		Predmet res = (Predmet) q.getSingleResult();
		em.close();
		return res;
	}

}
