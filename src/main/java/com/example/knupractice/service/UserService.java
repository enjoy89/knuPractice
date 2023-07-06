package com.example.knupractice.service;

import com.example.knupractice.domain.user.User;
import com.example.knupractice.repository.UserRepository;
import com.example.knupractice.web.dto.AddUserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserRequestDto requestDto) {
        return userRepository.save(
                User.builder()
                        .email(requestDto.getEamil())
                        .password(bCryptPasswordEncoder.encode(requestDto.getPassword()))
                        .build()).getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found"));
    }
}
