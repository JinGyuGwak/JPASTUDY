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
            Member member = new Member();
            member.setUsername("A");
            member.setAge(30);
            member.setRoleType(RoleType.ADMIN);

            Member member1 = new Member();
            member1.setUsername("B");
            member1.setAge(20);
            member1.setRoleType(RoleType.ADMIN);

            em.persist(member);
            em.persist(member1);


//            em.flush();
            System.out.println("플러시 됨");
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
