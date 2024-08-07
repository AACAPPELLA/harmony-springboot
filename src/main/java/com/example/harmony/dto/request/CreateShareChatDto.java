package com.example.harmony.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record CreateShareChatDto(@NotBlank String title, Long totalTime, List<CreateChatDto> chat,
                                 List<String> subjects, List<String> details, List<String> keywords) {
}
