package com.example.harmony.service;

import com.example.harmony.constant.Constant;
import com.example.harmony.domain.User;
import com.example.harmony.dto.ResponseDto;
import com.example.harmony.dto.request.CreateUserDto;
import com.example.harmony.dto.response.UserDto;
import com.example.harmony.exception.CommonException;
import com.example.harmony.exception.ErrorCode;
import com.example.harmony.repository.UserRepository;
import com.example.harmony.type.EProvider;
import com.example.harmony.type.ERole;
import com.example.harmony.util.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Boolean basicRegisterUser(CreateUserDto createUserDto) {

        User user = User.builder()
                .serialId(createUserDto.serialId())
                .password(passwordEncoder.encode(createUserDto.password()))
                .name(createUserDto.name())
                .phoneNumber(createUserDto.phoneNumber())
                .age(createUserDto.age())
                .eProvider(EProvider.DEFAULT)
                .role(ERole.USER)
                .build();

        userRepository.save(user);

        return Boolean.TRUE;
    }

    public ResponseDto<Boolean> withdrawalUser(Long userId) {
        User user =
                userRepository.findById(userId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        userRepository.delete(user);

        return ResponseDto.ok(Boolean.TRUE);
    }

    public String refreshAccessToken(String refreshToken) {
        UserRepository.UserSecurityForm user = userRepository.findByRefreshToken(refreshToken.substring(Constant.BEARER_PREFIX.length())).orElseThrow(
                () -> new CommonException(ErrorCode.NOT_FOUND_USER));
        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getRole(), jwtUtil.getAccessTokenExpriration());
        return accessToken;
    }

    public UserDto getUser(Long userId) {
        return UserDto.fromEntity(userRepository.findById(userId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER)));
    }

    @Transactional
    public Boolean updateUser(Long userId, CreateUserDto createUserDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        user.update(createUserDto.name(),passwordEncoder.encode(createUserDto.password()),
                createUserDto.phoneNumber(), createUserDto.age());
        return Boolean.TRUE;
    }

    @Transactional
    public Boolean updatePassword(Long userId, CreateUserDto createUserDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        user.update(passwordEncoder.encode(createUserDto.password()));
        return Boolean.TRUE;
    }

    public Boolean checkId(String serialId) {
        return userRepository.existsBySerialId(serialId);
    }

    public String checkUser(String serialId, String name, String phoneNumber) {
        User user = userRepository.findBySerialIdAndNameAndPhoneNumber(serialId, name, phoneNumber)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getRole(), jwtUtil.getAccessTokenExpriration());

        return accessToken;
    }

}
