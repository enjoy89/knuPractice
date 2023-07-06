package com.example.knupractice.web.controller;

import com.example.knupractice.service.TokenService;
import com.example.knupractice.web.dto.CreateAccessTokenResponseDto;
import com.example.knupractice.web.dto.CreateAcessTokenRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenApiController {

    // 액세스 토큰을 재발급
    // 리프레쉬 토큰

    private final TokenService tokenService;

    @PostMapping("/api/latest/token")
    public ResponseEntity<CreateAccessTokenResponseDto> createNewAccessToken(@RequestBody CreateAcessTokenRequestDto createAcessTokenRequestDto) {
        String newAccessToken = tokenService.createNewAccessToken(createAcessTokenRequestDto.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)

                .body(new CreateAccessTokenResponseDto(newAccessToken));
    }

    // 최초 유저 인증 or

    // refresh 토큰 자체가 만료되어서
}
