package com.example.harmony.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class JwtTokenDto extends SelfValidating<JwtTokenDto> {
    @NotBlank
    private String accessToken;

    @NotBlank
    private String refreshToken;

    public JwtTokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.validateSelf();
    }
}
