package com.example.harmony.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateChatDto(Long voiceId, @NotBlank String content, Long speaker) {
}
