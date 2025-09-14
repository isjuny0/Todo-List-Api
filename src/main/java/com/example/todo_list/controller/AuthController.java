package com.example.todo_list.controller;

import com.example.todo_list.dto.request.LoginRequestDto;
import com.example.todo_list.dto.request.SignupRequestDto;
import com.example.todo_list.dto.response.LoginResponseDto;
import com.example.todo_list.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthController {

    private final UserService userService;

    // 회원가입 페이지 보여주기
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupRequestDto", new SignupRequestDto());
        return "signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@ModelAttribute SignupRequestDto requestDto, Model model) {
        userService.signup(requestDto);
        model.addAttribute("message", "회원가입 성공! 로그인 해주세요.");
        return "login";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginRequestDto", new LoginRequestDto());
        return "login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequestDto requestDto, Model model) {
        LoginResponseDto loginResponseDto = userService.login(requestDto);
        model.addAttribute("message", "로그인 성공! 발급된 토큰: " + loginResponseDto);
        return "todos";
    }
}
