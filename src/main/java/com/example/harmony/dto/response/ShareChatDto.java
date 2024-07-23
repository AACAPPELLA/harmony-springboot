package com.example.harmony.dto.response;

import com.example.harmony.domain.ShareChat.ShareChat;

import java.util.List;
import java.util.stream.Collectors;

public record ShareChatDto(Long id, String title, String createdDate, Long totalTime, List<ChatDto> chat, SummaryDto summary)
{
    public static ShareChatDto from(ShareChat shareChat) {
        return new ShareChatDto(
            shareChat.getId(),
            shareChat.getTitle(),
            shareChat.getCreatedDate().toString(),
            shareChat.getTotalTime(),
            shareChat.getChat().stream().map(ChatDto::from).collect(Collectors.toList()),
            SummaryDto.from(shareChat.getSummary())
        );
    }
}
