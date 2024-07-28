package com.example.harmony.service;

import com.example.harmony.domain.ShareChat.Chat;
import com.example.harmony.domain.ShareChat.ShareChat;
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

import java.time.LocalDate;
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
                .subjects(createShareChatDto.subjects().stream().collect(Collectors.toSet()))
                .details(createShareChatDto.details().stream().collect(Collectors.toSet()))
                .keywords(createShareChatDto.keywords().stream().collect(Collectors.toSet()))
                .build();

        shareChatRepository.save(shareChat);
        for (Chat chat : chatList) {
            chat.setShareChat(shareChat);
        }
        return true;
    }

    @Transactional
    public List<ShareChatDto> getShareChat(Long userId, LocalDate date) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        List<ShareChat> shareChatList = shareChatRepository.findShareChatsByUserAndCreatedDate(user, date);


        return shareChatList.stream()
                .map(shareChat -> ShareChatDto.from(shareChat))
                .collect(Collectors.toList());
    }

    public Boolean deleteShare(Long userId, Long shareChatId) {
        ShareChat shareChat = shareChatRepository.findById(shareChatId).orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_SHARE_CHAT));
        if (userId != shareChat.getUser().getId()) {
            throw new CommonException(ErrorCode.ACCESS_DENIED_ERROR);
        }
        shareChatRepository.delete(shareChat);
        return true;
    }
}
