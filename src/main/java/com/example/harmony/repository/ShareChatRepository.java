package com.example.harmony.repository;

import com.example.harmony.domain.ShareChat.ShareChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShareChatRepository extends JpaRepository<ShareChat, Long> {
}
