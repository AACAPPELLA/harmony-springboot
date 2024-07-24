package com.example.harmony.dto.response;

import com.example.harmony.domain.User;
import com.example.harmony.type.ERole;

public record UserDto(Long id, String serialId, String name, String phoneNumber, String email, ERole role,
                      Integer age) {
    public static UserDto fromEntity(User user) {
        return new UserDto(
                user.getId(),
                user.getSerialId(),
                user.getName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getRole(),
                user.getAge()
        );
    }
}
