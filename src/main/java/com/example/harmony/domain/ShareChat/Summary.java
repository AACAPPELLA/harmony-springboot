package com.example.harmony.domain.ShareChat;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.util.List;

// 요약
@Getter
@NoArgsConstructor
@Embeddable
public class Summary {
    @ElementCollection
    @CollectionTable(name = "summary_subjects", joinColumns = @JoinColumn(name = "share_chat_id"))
    @Column(name = "subject")
    private List<String> subjects;

    @ElementCollection
    @CollectionTable(name = "summary_details", joinColumns = @JoinColumn(name = "share_chat_id"))
    @Column(name = "detail")
    private List<String> details;

    @ElementCollection
    @CollectionTable(name = "summary_keywords", joinColumns = @JoinColumn(name = "share_chat_id"))
    @Column(name = "keyword")
    private List<String> keywords;

    @Builder
    public Summary(List<String> subjects, List<String> details, List<String> keywords) {
        this.subjects = subjects;
        this.details = details;
        this.keywords = keywords;
    }
}
