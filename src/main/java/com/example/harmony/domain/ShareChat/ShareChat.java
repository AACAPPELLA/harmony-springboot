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
import java.util.Set;

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

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "summary_subjects", joinColumns = @JoinColumn(name = "share_chat_id"))
    @Column(name = "subject")
    private Set<String> subjects;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "summary_details", joinColumns = @JoinColumn(name = "share_chat_id"))
    @Column(name = "detail")
    private Set<String> details;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "summary_keywords", joinColumns = @JoinColumn(name = "share_chat_id"))
    @Column(name = "keyword")
    private Set<String> keywords;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public ShareChat(User user,String title, Long totalTime, List<Chat> chat, Set<String> subjects, Set<String> details, Set<String> keywords) {
        this.user = user;
        this.title = title;
        this.createdDate = LocalDateTime.now();
        this.totalTime = totalTime;
        this.chat = chat;
        this.subjects = subjects;
        this.details = details;
        this.keywords = keywords;

    }
}

