package com.example.knupractice.jpaPractice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT distinct t FROM Team t join t.members")
    List<Team> findAllWithMemberUsingJoin();

    @Query("SELECT distinct t FROM Team t join fetch t.members")
        // JPQL
    List<Team> findAllWithMemberUsingFetchJoin();

    @Query("SELECT distinct t FROM Team t join t.members m where m.name = :memberName")
    List<Team> findByMemberNameWithMemberUsingJoin(String memberName);
}
