package com.example.knupractice.web.controller;

import com.example.knupractice.service.UserService;
import com.example.knupractice.web.dto.AddUserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/user")
    public String signUp(AddUserRequestDto requestDto) {
        userService.save(requestDto);
        return "redirect:/login";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(
                request,
                response,
                SecurityContextHolder.getContext().getAuthentication()
        );

        return "redirect:/login";
    }
}
