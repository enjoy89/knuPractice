package com.example.knupractice.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateAccessTokenResponseDto {
    private String accessToken;
    public CreateAccessTokenResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }



}
