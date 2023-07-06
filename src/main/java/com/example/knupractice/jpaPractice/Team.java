package com.example.knupractice.jpaPractice;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// Team : Member
// 1 : N (일대다 양방향 관계)
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @Column(name = "team_name")
    private String name;

    // 일대다 관계 (1:N)
    // 주인이 아닌 쪽인 Team 객체에 mappedBy를 사용
    // team.getMembers() 와 같이 역참조가 가능해진다.
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<Member> members = new ArrayList<>(); // 조회만 가능함

    public void addMember(Member member) {
        member.setTeam(this);     // 연관관계의 주인쪽에서 연관관계를 설정해주는 코드 추가
        members.add(member);
    }
}
