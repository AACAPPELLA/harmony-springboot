package com.example.harmony.dto.response;

import com.example.harmony.domain.ShareChat.Summary;

import java.util.List;

public record SummaryDto(List<String> subjects, List<String> details, List<String> keywords) {
    public static SummaryDto from(Summary summary) {
        return new SummaryDto(
            summary.getSubjects(),
            summary.getDetails(),
            summary.getKeywords()
        );
    }
}
