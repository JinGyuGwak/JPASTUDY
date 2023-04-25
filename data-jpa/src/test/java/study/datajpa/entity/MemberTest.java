package study.datajpa.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity(){
        Team teamA = new Team("TeamA");
        Team teamB = new Team("TeamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1",10,teamA);
        Member member2 = new Member("member2",20,teamA);
        Member member3 = new Member("member3",30,teamB);
        Member member4 = new Member("member4",40,teamB);



        em.persist(member1); //영속성컨텍스트에서 관리
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);



        em.flush(); //영속성컨텍스트의 내용을 DB에 날림
        em.clear();

        Member member5 = em.find(Member.class, 3);
        member5.changeTeam(teamA);

        em.persist(member5);
        em.flush();


        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        for (Member member : members) {
            System.out.println("member = " + member);
            System.out.println("member.getTeam() = " + member.getTeam());
        }
        List<Member> memberLi = em.createQuery("select m from Member m where m.team=:team", Member.class)
                .setParameter("team",teamB)
                .getResultList();

        System.out.println("memberLi.size() = " + memberLi.size());

    }
}