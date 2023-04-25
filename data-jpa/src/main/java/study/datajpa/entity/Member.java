package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of={"id","username","age"})
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="team_id")
    private Team team;

    public Member(String username, int age, Team team) {
        this.username=username;
        this.age=age;
        if(team!=null){
            changeTeam(team);
        }
    }

    public void changeTeam(Team team){
        this.team=team;
        team.getMembers().add(this);
        //바뀌기 전의 팀의 멤버는 수정이 안되지 않나 -> 안해도 JPA 가 자동으로 바꿔주네 신기하다
        //하지만 flush하기 전에는 수정되지 않으므로 연관관계 생성자 해주는 것이 맞는 것 같기도
    }


    public Member(String username) {
        this.username = username;
    }
}
