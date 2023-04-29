package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;
    @Autowired TeamRepository teamRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId()).get();
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }
    @Test
    public void basicCRUD(){
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        //생성
        memberRepository.save(member1);
        memberRepository.save(member2);

        //조회
        Member findMember1 = memberRepository.findById(member1.getId()).get();
        Member findMember2 = memberRepository.findById(member2.getId()).get();
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);

        //리스트 조회
        List<Member> all = memberRepository.findAll();
        assertThat(all.size()).isEqualTo(2);

        //카운트
        long count = memberRepository.count();
        assertThat(count).isEqualTo(2);

        //삭제
        memberRepository.delete(member1);
        long deleteCount = memberRepository.count();
        assertThat(deleteCount).isEqualTo(1);
    }
    @Test
    public void testQuery(){
        Member member1 = new Member("AAA",10);
        Member member2 = new Member("BBB",20);

        //생성
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findUser("AAA", 10);
        assertThat(result.get(0)).isEqualTo(member1);

    }
    @Test
    public void findMemberDto(){
        Team team = new Team("teamA");
        teamRepository.save(team);

        Member m1 = new Member("AAA",10);
        memberRepository.save(m1);
        m1.setTeam(team);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto dto : memberDto) {
            System.out.println("dto = " + dto);
        }
    }

    @Test
    public void findByNames(){
        Member member1 = new Member("AAA",10);
        Member member2 = new Member("BBB",20);

        //생성
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findByNames(Arrays.asList("AAA", "BBB"));
        for (Member member : result) {
            System.out.println("member = " + member);
        }
    }

    @Test
    public void returnType(){
        Member member1 = new Member("AAA",10);
        Member member2 = new Member("BBB",20);

        //생성
        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> result = memberRepository.findListByUsername("AAA");
        Member member = memberRepository.findMemberByUsername("AAA");
        Optional<Member> kkk = memberRepository.findOptionalByUsername("BBB");

    }
    @Test
    public void paging(){
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",10));
        memberRepository.save(new Member("member3",10));
        memberRepository.save(new Member("member4",10));
        memberRepository.save(new Member("member5",10));

        int age=10;
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));

        Page<Member> page = memberRepository.findByAge(age,pageRequest);

        Page<MemberDto> toMap = page.map(m -> new MemberDto(m.getId(), m.getUsername(), null));

        List<Member> content = page.getContent();
        for (Member member : content) {
            System.out.println("member = " + member);
        }
    }
    @Test
    public void bulkUpdate(){
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",19));
        memberRepository.save(new Member("member3",20));
        memberRepository.save(new Member("member4",21));
        memberRepository.save(new Member("member5", 40));
        em.flush();
        em.clear();

        Member member5 = memberRepository.findByUsername("member5");
        member5.setAge(50);
        int resultCount = memberRepository.bulkAgePlus(20);

        member5.setAge(50); //영속성 컨텍스트에 있는 member5의 age값을 50으로 바꿈

        em.flush();
        em.clear();


        Member result = memberRepository.findByUsername("member5");
        System.out.println("result = " + result.getAge());

        assertThat(resultCount).isEqualTo(3);

    }
}