package com.example.harmony.controller;

import com.example.harmony.annotation.UserId;
import com.example.harmony.dto.ResponseDto;
import com.example.harmony.dto.request.CreateUserDto;
import com.example.harmony.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;

    //회원정보 가져오기
    @GetMapping("")
    public ResponseDto<?> basicRegister(@UserId Long userId) {
        return ResponseDto.ok(userService.getUser(userId));
    }


    //회원탈퇴
    @DeleteMapping("")
    public ResponseDto<?> withdrawalUser(@UserId Long userId) {
        return ResponseDto.ok(userService.withdrawalUser(userId));
    }


}
