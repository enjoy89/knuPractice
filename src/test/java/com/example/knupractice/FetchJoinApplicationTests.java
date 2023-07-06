package com.example.knupractice;

import com.example.knupractice.jpaPractice.Team;
import com.example.knupractice.jpaPractice.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FetchJoinApplicationTests {
  @Autowired
  private TeamService teamService;

  // 구성
  // Team 1 : member1, member2, member3
  // Team 2 : member4, member5
  @BeforeEach
  public void init(){
    teamService.initialize();
  }

  @Test
  /**
   * join을 했는데 각 Team의 Lazy entity인 members가 아직 초기화 되지 않았다는 상태를 보인다.
   * 실제로 일반 join은 실제 쿼리에 join을 걸어주지만 join 대상에 대한 영속성까지는 관여하지 않는다.
   */
  public void joinTest() {
    // 1. 일반 join으로 Team entity 초기화 완료
    // 2. 하지만 일반 join은 연관 Entity까지 초기화하지 않기 때문에 Member 초기화 X
    // 3. toString()으로 초기화되지 않은 members 접근하므로 LazyInitializationException
    // 4. Team에 @ToString(exclude="members") 하면 예외 발생 안함
    List<Team> memberUsingJoin = teamService.findAllWithMemberUsingJoin();
    //break point
    System.out.println(memberUsingJoin);
  }

  @Test
  /**
   * 일반 조인과 형태는 같지만 SELECT 컬럼부터 차이가 발생한다.
   * 일반 join : 실제 질의하는 대상 Entity(team) 컬럼만 Select
   * fetch join : 대상 Entity와 fetch join이 걸린 Entity 함께 select
   * 따라서 예외가 발생하지 않는다.
   */
  public void fetchJoinTest() {
    List<Team> memberUsingFetchJoin = teamService.findAllWithMemberUsingFetchJoin();
    //break point
    System.out.println(memberUsingFetchJoin);
  }

  @Test
  public void joinConditionTest() {
    List<Team> memberUsingJoin = teamService.findByMemberNameWithMemberUsingJoin("member4");
    //break point
    System.out.println(memberUsingJoin);
  }
  
}