package com.example.knupractice.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 요청 헤더의 Authoriztion key 값 조회
        String authoriztionHeader = request.getHeader(HEADER_AUTHORIZATION);

        // 가져온 값에서 접두사 제거 -> 토큰값만 추출함
        String token = getAccessToken(authoriztionHeader);

        // 가져온 토큰값이 유효한지 확인하고, 유효하다면 인증 정보를 만든다.
        if(tokenProvider.validToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);

            // SecurityContextHodler 안에 있는 SecurityContext 안에 있는 Authentication을 지정해준다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);

    }

    private String getAccessToken(String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
