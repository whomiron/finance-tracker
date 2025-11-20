package com.example.finance_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponse {
    private List<String> highlights;
    private List<String> actions;
    private List<String> questionsToConsider;
}
