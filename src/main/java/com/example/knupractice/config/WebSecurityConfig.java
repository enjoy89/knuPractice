package com.example.knupractice.config;

import com.example.knupractice.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailService userDetailService;

    // 스프링 시큐리티 기능 비활성화 부분 설정
    @Bean
    public WebSecurityCustomizer configure() {
        return (web -> web.ignoring()
                .requestMatchers(toH2Console())
                .antMatchers("/static/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests() // 인증, 인가 설정
                .antMatchers("/login", "/signup", "/user").permitAll() // 일단은 다 접근할 수 있도록 허용
                .anyRequest().authenticated() // 이외에 모든 것들은 인증이 필요함

                .and()
                .formLogin()  // 폼 로그인 인증 기반
                .loginPage("/login")
                .defaultSuccessUrl("/articles")

                .and()
                .logout() // 로그아웃 설정
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)

                .and()
                .csrf().disable() // csrf : 보안과 관련되어 있는 무언가
                .build();
    }

    // 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity, BCryptPasswordEncoder bCryptPasswordEncoder
    , UserDetailService userDetailService) throws Exception {
        return httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and()
                .build();

    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
