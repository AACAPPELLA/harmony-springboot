package com.example.harmony.controller;

import com.example.harmony.dto.ResponseDto;
import com.example.harmony.dto.request.CreateUserDto;
import com.example.harmony.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    //기본 회원가입
    @PostMapping("/register")
    public ResponseDto<?> basicRegisterUser(@RequestBody @Valid CreateUserDto createUserDto) {
        return ResponseDto.created(userService.basicRegisterUser(createUserDto));
    }

    // 아이디 중복확인
    @GetMapping("/check")
    public ResponseDto<?> checkId(@RequestParam String serialId) {
        return ResponseDto.ok(userService.checkId(serialId));
    }

    //Refresh Token 으로 Access Token 재발급
    @PostMapping("/refresh")
    public ResponseDto<?> refreshAccessToken(@RequestHeader("Authorization") String refreshToken) {
        return ResponseDto.ok(userService.refreshAccessToken(refreshToken));
    }

}
