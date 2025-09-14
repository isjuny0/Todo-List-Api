package com.example.todo_list.service;

import com.example.todo_list.dto.request.LoginRequestDto;
import com.example.todo_list.dto.request.SignupRequestDto;
import com.example.todo_list.dto.response.LoginResponseDto;
import com.example.todo_list.entity.User;
import com.example.todo_list.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserService {

    private final UserRepository userRepository;

    public void signup(SignupRequestDto requestDto) {
        User user = User.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .build();

        userRepository.save(user);
    }

    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!user.getPassword().equals(requestDto.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        // 추후 jwt 토큰 생성으로 교체
        String token = UUID.randomUUID().toString();
        return new LoginResponseDto(token);
    }
}
