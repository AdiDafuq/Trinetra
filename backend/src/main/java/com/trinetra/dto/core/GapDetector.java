package com.trinetra.service.analysis; // Fixed: Correct business logic package location

import com.trinetra.service.ai.OllamaService;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class GapDetector {

    private final OllamaService ollamaService;

    public GapDetector(OllamaService ollamaService) {
        this.ollamaService = ollamaService;
    }

    public List<String> detect(String transcript) {
        // Defensive check to avoid processing empty strings
        if (transcript == null || transcript.isBlank()) {
            return Collections.singletonList("Missing causal reasoning due to empty transcript input.");
        }

        String prompt = "Identify missing reasoning gaps in the following transcript:\n\n" + transcript;
        String response = ollamaService.analyzeWithModel(prompt);

        if (response == null || response.isBlank()) {
            return Collections.singletonList("No discernible analysis feedback could be parsed from the model.");
        }

        return Arrays.stream(response.split("\n"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}
