package com.example.harmony.dto.request;

import java.util.List;

public record CreateSummaryDto(List<String> subjects, List<String> details, List<String> keywords) {
}
