package com.example.harmony.domain.ShareChat;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "chats")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Getter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "voice_id")
    private Long voiceId;

    @Column(name = "content")
    private String content;

    @Column(name = "speaker")
    private Long speaker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "share_chat_id")
    private ShareChat shareChat;

    @Builder
    public Chat(Long voiceId, String content, Long speaker) {
        this.voiceId = voiceId;
        this.content = content;
        this.speaker = speaker;
    }

    public void setShareChat(ShareChat shareChat) {
        this.shareChat = shareChat;
    }
}
