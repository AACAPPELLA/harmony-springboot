package com.example.harmony.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(@NotBlank String serialId, @NotBlank String password,
                            String name, String phoneNumber, Integer age) {
}
