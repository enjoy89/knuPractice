package com.example.knupractice.config.jwt;

import com.example.knupractice.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

// 토큰 생성, 검증
@Service
@RequiredArgsConstructor
public class TokenProvider {
    private final JwtProperies jwtProperies;

    // payload에 들어갈 유저 정보와 유효 시간
    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();


        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // private으로 정보은닉
    private String makeToken(Date expiry, User user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 타입 : JWT
                .setIssuer(jwtProperies.getIssuer())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .setSubject(user.getEmail())
                .claim("id", user.getId())
                .signWith(SignatureAlgorithm.HS256, jwtProperies.getSercet())
                .compact();
    }

    // 토근의 유효성 검증
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperies.getSercet())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰 기반으로 인증 정보를 가져오는 작업
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        // Authentication - principal, credentials, authorities
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(
                new org.springframework.security.core.userdetails.User(
                        claims.getSubject(), "", authorities), // principal
                token,
                authorities
        );

    }

    public Long getUserId(String token) {
        Claims claims = getClaims(token);

        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtProperies.getSercet())
                .parseClaimsJws(token)
                .getBody();

    }
}
