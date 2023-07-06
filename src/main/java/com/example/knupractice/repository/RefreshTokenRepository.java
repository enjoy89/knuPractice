package com.example.knupractice.repository;

import com.example.knupractice.domain.token.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 리프레시 토큰 레파지토리는 필요하고
// 엑세스 토큰 레파지토리는 필요하지 않음 - 어차피 유효기간도 짧고, 엑세스 토큰을 통해 재발급하면 되기 때문에 저장하고 있지는 않아도됨
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUserId(Long userId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

}
