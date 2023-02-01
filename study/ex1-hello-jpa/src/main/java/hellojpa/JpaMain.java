package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //code
        try{
            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            //DBConnection.executeQuery("select * from member");
            //결과 없음

            tx.commit();
        }   catch(Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }


}
