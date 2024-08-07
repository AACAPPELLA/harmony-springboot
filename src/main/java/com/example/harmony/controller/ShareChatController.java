package com.example.harmony.controller;

import com.example.harmony.annotation.Date;
import com.example.harmony.annotation.UserId;
import com.example.harmony.dto.ResponseDto;
import com.example.harmony.dto.request.CreateShareChatDto;
import com.example.harmony.service.ShareChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shares")
@Slf4j
public class ShareChatController {
    private final ShareChatService shareChatService;

    @GetMapping("/{date}")
    public ResponseDto<?> getShare(@UserId Long userId, @Date @Valid @PathVariable String date) {
        return ResponseDto.ok(shareChatService.getShareChat(userId, LocalDate.parse(date)));
    }

    @PostMapping("")
    public ResponseDto<?> createShare(@UserId Long userId, @RequestBody @Valid CreateShareChatDto createShareChatDto) {
        return ResponseDto.created(shareChatService.createShareChat(userId, createShareChatDto));
    }

    @DeleteMapping("/{shareChatId}")
    public ResponseDto<?> deleteShare(@UserId Long userId, @PathVariable Long shareChatId) {
        return ResponseDto.ok(shareChatService.deleteShare(userId, shareChatId));
    }

}
