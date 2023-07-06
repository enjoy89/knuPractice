package com.example.knupractice.repository;

import com.example.knupractice.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일 방식으로 회원 조회
    Optional<User> findByEmail(String email);
}
