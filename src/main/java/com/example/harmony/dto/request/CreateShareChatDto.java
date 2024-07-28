package com.example.harmony.dto.request;

import java.util.List;

public record CreateShareChatDto(String title, Long totalTime, List<CreateChatDto> chat,
                                  List<String> subjects, List<String> details, List<String> keywords) {
}
