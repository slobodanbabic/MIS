package crud;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import model.Ispit;
import utils.PersistenceUtil;

public class IspitCrud {

	public void insertIspit(Ispit i){
		EntityManager em = PersistenceUtil.getEntityManager();
		EntityTransaction et = null;		
		try{
			et = em.getTransaction();
			et.begin();
			em.persist(i);
			em.flush();
			et.commit();
		}catch(Exception ex){
			ex.printStackTrace();
			if(et != null){
				et.rollback();
			}
		}finally{
			if(em != null && em.isOpen()){
				em.close();
			}
		}		
	}
	

}
