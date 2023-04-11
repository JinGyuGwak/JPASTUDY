package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();


        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            //비영속 상태
            Member member1 = new Member(4L,"Hello");
            Member member2 = new Member(5L, "B");

            //영속 상태
            em.persist(member1);
            em.persist(member2);



            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

        System.out.println("실행이 되었어요");

    }
}
