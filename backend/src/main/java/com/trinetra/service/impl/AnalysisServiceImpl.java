package com.trinetra.service.impl;

import java.util.Arrays;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import com.trinetra.dto.core.EvidenceDTO;
import com.trinetra.dto.core.KPIMappingDTO;
import com.trinetra.dto.core.ScoreDTO;
import com.trinetra.dto.request.TranscriptRequestDTO;
import com.trinetra.dto.response.AnalysisResponseDTO;
import com.trinetra.service.AnalysisService;
import com.trinetra.service.ai.OllamaService;
import com.trinetra.service.ai.PromptBuilderService;
import com.trinetra.service.parser.JsonParserService;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    private final PromptBuilderService promptBuilderService;
    private final OllamaService ollamaService;
    private final JsonParserService jsonParserService;

    public AnalysisServiceImpl(
            PromptBuilderService promptBuilderService,
            OllamaService ollamaService,
            JsonParserService jsonParserService
    ) {
        this.promptBuilderService = promptBuilderService;
        this.ollamaService = ollamaService;
        this.jsonParserService = jsonParserService;
    }

    @Override
    public AnalysisResponseDTO analyzeTranscript(TranscriptRequestDTO request) {

        String transcript = request.getTranscript();

        String prompt = promptBuilderService.buildScoringPrompt(transcript);

        String raw = ollamaService.analyzeWithModel(prompt);

        JsonNode json = jsonParserService.parse(raw);

        // =========================
        // SAFE EXTRACTION (NO CRASHES)
        // =========================
        String summary = json.path("summary").asText("No summary provided");

        int scoreValue = json.path("score").asInt(0);
        String reasoningLevel = json.path("reasoning_level").asText("unknown");
        String survivability = json.path("survivability").asText("unknown");

        JsonNode biasNode = json.path("bias_flags");

        String helpfulnessBias = biasNode.path("helpfulness_bias").asText("unknown");
        String presenceBias = biasNode.path("presence_bias").asText("unknown");

        // =========================
        // CORE DTOs
        // =========================
        ScoreDTO score = new ScoreDTO(
                "overall",
                scoreValue,
                "reasoning=" + reasoningLevel + " | survivability=" + survivability
        );

        EvidenceDTO evidence = new EvidenceDTO(
                "bias_summary",
                "helpfulness=" + helpfulnessBias + ", presence=" + presenceBias
        );

        KPIMappingDTO kpi = new KPIMappingDTO(
                "LLM Evaluation",
                "Prompt-based structured scoring completed"
        );

        // =========================
        // FINAL RESPONSE (CLEAN CONTRACT)
        // =========================
        return new AnalysisResponseDTO(
                summary,
                Arrays.asList(score),
                Arrays.asList(evidence),
                Arrays.asList(kpi),
                Arrays.asList(),
                Arrays.asList()
        );
    }
}