package com.trinetra.dto.core;

import com.trinetra.service.ai.OllamaService;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class EvidenceExtractor {

    private final OllamaService ollamaService;

    public EvidenceExtractor(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    public List<String> extract(String transcript) {
        if (transcript == null || transcript.isBlank()) {
            return Collections.emptyList();
        }

        String prompt = "Extract ONLY factual evidence points as bullet list.\n\nTranscript:\n" + transcript;
        String response = ollamaService.analyzeWithModel(prompt);

        if (response == null || response.isBlank()) {
            return Collections.emptyList();
        }

        return Arrays.stream(response.split("\n"))
                .map(String::trim)
                // Cleans out markdown prefix indicators (*, -, or numerical sequences)
                .map(s -> s.replaceAll("^([-*]|\\d+\\.)\\s+", ""))
                .filter(s -> !s.isEmpty())
                .toList();
    }
}