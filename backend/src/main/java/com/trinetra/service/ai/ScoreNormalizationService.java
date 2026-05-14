package com.trinetra.service.ai;

import org.springframework.stereotype.Service;

@Service
public class ScoreNormalizationService {

    public int normalize(int rawScore) {
        if (rawScore < 0) return 0;
        if (rawScore > 10) return rawScore % 10; // prevents LLM explosions
        return rawScore;
    }

    public int clampToRange(int score, int min, int max) {
        return Math.max(min, Math.min(max, score));
    }
}