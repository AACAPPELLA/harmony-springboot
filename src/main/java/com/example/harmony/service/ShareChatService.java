package com.example.harmony.service;

import com.example.harmony.domain.ShareChat.Chat;
import com.example.harmony.domain.ShareChat.ShareChat;
import com.example.harmony.domain.ShareChat.Summary;
import com.example.harmony.domain.User;
import com.example.harmony.dto.request.CreateShareChatDto;
import com.example.harmony.dto.response.ShareChatDto;
import com.example.harmony.exception.CommonException;
import com.example.harmony.exception.ErrorCode;
import com.example.harmony.repository.ShareChatRepository;
import com.example.harmony.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShareChatService {
    private final ShareChatRepository shareChatRepository;
    private final UserRepository userRepository;

    @Transactional
    public Boolean createShareChat(Long userId, CreateShareChatDto createShareChatDto) {

        User user = userRepository.findById(userId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        List<Chat> chatList = createShareChatDto.chat().stream()
                .map(chat -> Chat.builder()
                        .voiceId(chat.voiceId())
                        .content(chat.content())
                        .speaker(chat.speaker())
                        .build())
                .collect(Collectors.toList());

        ShareChat shareChat = ShareChat.builder()
                .user(user)
                .title(createShareChatDto.title())
                .totalTime(createShareChatDto.totalTime())
                .chat(chatList)
                .summary(Summary.builder()
                        .details(createShareChatDto.summary().details())
                        .keywords(createShareChatDto.summary().keywords())
                        .subjects(createShareChatDto.summary().subjects())
                        .build())
                .build();

        shareChatRepository.save(shareChat);
        for (Chat chat : chatList) {
            chat.setShareChat(shareChat);
        }
        return true;
    }

    public ShareChatDto getShareChat(Long shareChatId) {
        ShareChat shareChat = shareChatRepository.findById(shareChatId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_SHARE_CHAT));

        return ShareChatDto.from(shareChat);
    }

    public Boolean deleteShare(Long userId,Long shareChatId) {
        ShareChat shareChat = shareChatRepository.findById(shareChatId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_SHARE_CHAT));
        if (userId != shareChat.getUser().getId()) {
            throw new CommonException(ErrorCode.ACCESS_DENIED_ERROR);
        }
        shareChatRepository.delete(shareChat);
        return true;
    }
}
