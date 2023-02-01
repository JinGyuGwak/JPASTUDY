package jpql;

import javax.persistence.*;
import java.util.Collection;
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
            Team team1 = new Team();
            team1.setName("팀A");
            em.persist(team1);

            Team team2 = new Team();
            team2.setName("팀B");
            em.persist(team2);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setAge(0);
            member1.setTeam(team1);
            em.persist(member1);


            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(0);
            member2.setTeam(team1);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("member3");
            member3.setAge(0);
            member3.setTeam(team2);
            em.persist(member3);

            Member member4 = new Member();
            member4.setUsername("member4");
            em.persist(member4);

            em.flush();
            em.clear();

            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            System.out.println("resultCount = " + resultCount);

            System.out.println("member1.getAge() = " + member1.getAge());
            System.out.println("member1.getAge() = " + member2.getAge());
            System.out.println("member1.getAge() = " + member3.getAge());


            System.out.println("플러시 후 member1 = " + member1.getAge());

            tx.commit();
        }   catch(Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }


}
