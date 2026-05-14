package com.trinetra.service.ai;

import org.springframework.stereotype.Service;

@Service
public class PromptSafetyService {

    public String sanitizeTranscript(String transcript) {
        if (transcript == null) return "";

        return transcript
                .replaceAll("\\s+", " ")
                .replaceAll("[^a-zA-Z0-9.,!?%:;()\\-\\s]", "")
                .trim();
    }

    public String enforceScoringBounds(String llmOutput) {
        if (llmOutput == null) return "{}";

        // crude safety net in case model goes crazy
        return llmOutput
                .replaceAll("(score\\s*:\\s*)([0-9]{2,})", "$11$2");
    }
}