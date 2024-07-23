package com.example.harmony.domain.ShareChat;

import com.example.harmony.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "share_chats")
public class ShareChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "total_time")
    private Long totalTime;

    @OneToMany(mappedBy = "shareChat", cascade = CascadeType.ALL)
    private List<Chat> chat;

    @Embedded
    private Summary summary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public ShareChat(User user,String title, Long totalTime, List<Chat> chat, Summary summary) {
        this.user = user;
        this.title = title;
        this.createdDate = LocalDateTime.now();
        this.totalTime = totalTime;
        this.chat = chat;
        this.summary = summary;
    }
}

