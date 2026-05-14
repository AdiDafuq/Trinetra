package com.trinetra.service.ai.impl;

import com.trinetra.dto.ollama.OllamaRequestDTO;
import com.trinetra.dto.ollama.OllamaResponseDTO;
import com.trinetra.service.ai.OllamaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class OllamaServiceImpl implements OllamaService {

    // 1. Fixed: Native SLF4J Logger prevents Lombok initialization crashes
    private static final Logger log = LoggerFactory.getLogger(OllamaServiceImpl.class);

    private final WebClient ollamaWebClient;

    @Value("${ollama.model}")
    private String ollamaModel;

    // 2. Fixed: Manual constructor guarantees parameter registration without @RequiredArgsConstructor
    public OllamaServiceImpl(WebClient ollamaWebClient) {
        this.ollamaWebClient = ollamaWebClient;
    }

    @Override
    public String analyzeWithModel(String prompt) {

        // =========================
        // ATTEMPT 1 (NORMAL CALL)
        // =========================
        String response = tryCall(prompt);

        if (isValid(response)) {
            return response;
        }

        log.warn("Ollama returned invalid response. Retrying with stricter prompt...");

        // =========================
        // ATTEMPT 2 (STRICT MODE)
        // =========================
        String strictPrompt = buildStrictPrompt(prompt);
        response = tryCall(strictPrompt);

        if (isValid(response)) {
            return response;
        }

        log.error("Ollama failed after retry. Using fallback response.");

        // =========================
        // FALLBACK (NEVER BREAK PIPELINE)
        // =========================
        return fallbackResponse();
    }

    // =====================================================
    // CORE CALL
    // =====================================================
    private String tryCall(String prompt) {
        try {
            // 3. Fixed: Normal object creation bypasses the non-functional '.builder()' setup
            OllamaRequestDTO request = new OllamaRequestDTO();
            request.setModel(ollamaModel);
            request.setPrompt(prompt);
            request.setStream(false);
            request.setNum_predict(500);

            OllamaResponseDTO response = ollamaWebClient
                    .post()
                    .uri("/api/generate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(OllamaResponseDTO.class)
                    .block();

            if (response == null) return null;

            return response.getResponse();

        } catch (WebClientResponseException e) {
            log.error("Ollama API error: {}", e.getStatusCode());
            return null;

        } catch (Exception e) {
            log.error("Ollama connection failed: {}", e.getMessage());
            return null;
        }
    }

    // =====================================================
    // STRICT PROMPT WRAPPER (forces structure discipline)
    // =====================================================
    private String buildStrictPrompt(String originalPrompt) {
        return """
                IMPORTANT SYSTEM RULE:

                - Output ONLY valid JSON
                - No explanations
                - No extra text
                - No markdown

                CRITICAL:
                If you fail to follow JSON format, response will be rejected.

                ORIGINAL TASK:
                %s
                """.formatted(originalPrompt);
    }

    // =====================================================
    // RESPONSE VALIDATION (lightweight guard)
    // =====================================================
    private boolean isValid(String response) {
        if (response == null) return false;

        String trimmed = response.trim();

        return trimmed.startsWith("{") && trimmed.endsWith("}");
    }

    // =====================================================
    // FALLBACK (safe system behavior)
    // =====================================================
    private String fallbackResponse() {
        return """
        {
          "summary": "Fallback response due to model failure",
          "score": 6,
          "location": "evaluation_environment",
          "reasoning_level": "medium",
          "survivability": "medium",
          "bias_flags": {
            "helpfulness_bias": "medium",
            "presence_bias": "medium"
          },
          "key_evidence": [],
          "gaps": [
            {
              "gap": "Model failure fallback triggered",
              "impact": "reduced reliability"
            }
          ]
        }
        """;
    }
}
