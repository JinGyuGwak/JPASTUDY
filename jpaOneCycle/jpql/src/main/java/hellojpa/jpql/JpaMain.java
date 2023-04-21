package hellojpa.jpql;



import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();


        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Team team1 = new Team();
            team1.setName("TeamA");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("TeamB");
            em.persist(team2);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team1);
            member1.setAge(20);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(team1);
            member2.setAge(20);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setTeam(team2);
            member3.setAge(20);
            em.persist(member3);


            em.flush();
            em.clear();

            int resultCount = em.createQuery("update Member m set m.age=30")
                    .executeUpdate();


            tx.commit();
        } catch (Exception e){
            System.out.println("실행이 안되었어요");
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

        System.out.println("실행이 되었어요");

    }
}
