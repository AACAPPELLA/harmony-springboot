package com.example.harmony.dto.response;

import com.example.harmony.domain.ShareChat.Chat;

public record ChatDto(Long voiceId, String content,Long speaker) {
    public static ChatDto from(Chat chat) {
        return new ChatDto(
            chat.getVoiceId(),
            chat.getContent(),
            chat.getSpeaker()
        );
    }
}
