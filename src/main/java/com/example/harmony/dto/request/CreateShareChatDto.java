package com.example.harmony.dto.request;

import java.util.List;

public record CreateShareChatDto(String title, Long totalTime, List<CreateChatDto> chat, CreateSummaryDto summary) {
}
