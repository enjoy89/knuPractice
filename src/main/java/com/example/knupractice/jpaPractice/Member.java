package com.example.knupractice.jpaPractice;

import lombok.*;

import javax.persistence.*;

// Member : Team
// N : 1 (다대일 양방향 관계)
// 연관관계의 주인은 Member
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name")
    private String name;

    @Column(name = "member_age")
    private int age;

    // 연관관계의 주인인 Member는 외래키를 가질 수 있다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")   // 외래키로 team_id를 참조한다.
    @ToString.Exclude
    public Team team;

    public Member(String name, int age, Team team) {
        this.name = name;
        this.age = age;
        this.team = team;
    }

}
