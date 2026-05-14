package com.trinetra.service.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.trinetra.dto.core.EvidenceDTO;
import com.trinetra.dto.core.KPIMappingDTO;
import com.trinetra.dto.core.ScoreDTO;
import com.trinetra.dto.request.TranscriptRequestDTO;
import com.trinetra.dto.response.AnalysisResponseDTO;
import com.trinetra.service.parser.JsonParserService;
import com.trinetra.service.analysis.GapDetector;               // Fixed: Import real processing engine
import com.trinetra.service.analysis.BiasDetector;              // Fixed: Import real processing engine
import com.trinetra.service.analysis.EvidenceExtractor;         // Fixed: Import real processing engine
import com.trinetra.service.analysis.AnalysisMetricsCalculator; // Fixed: Import real math calculator
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalysisPipelineService {

    private final OllamaService ollamaService;
    private final PromptBuilderService promptBuilderService;
    private final JsonParserService jsonParserService;
    
    // Core functional evaluation components
    private final GapDetector gapDetector;
    private final BiasDetector biasDetector;
    private final EvidenceExtractor evidenceExtractor;
    private final AnalysisMetricsCalculator metricsCalculator;

    // Explicit structural constructor injection
    public AnalysisPipelineService(
            OllamaService ollamaService,
            PromptBuilderService promptBuilderService,
            JsonParserService jsonParserService,
            GapDetector gapDetector,
            BiasDetector biasDetector,
            EvidenceExtractor evidenceExtractor,
            AnalysisMetricsCalculator metricsCalculator
    ) {
        this.ollamaService = ollamaService;
        this.promptBuilderService = promptBuilderService;
        this.jsonParserService = jsonParserService;
        this.gapDetector = gapDetector;
        this.biasDetector = biasDetector;
        this.evidenceExtractor = evidenceExtractor;
        this.metricsCalculator = metricsCalculator;
    }

    public AnalysisResponseDTO analyze(TranscriptRequestDTO request) {

        // =========================
        // 1. MAIN LLM ANALYSIS
        // =========================
        String basePrompt = promptBuilderService.buildPrompt(request);
        String rawResponse = ollamaService.analyzeWithModel(basePrompt);

        JsonNode json = jsonParserService.parse(rawResponse);

        String summary = json.path("summary").asText("No summary provided");
        String sentiment = json.path("sentiment").asText("unknown");

        int rawScore = json.path("score").asInt(0);
        int score = clamp(rawScore, 0, 10);

        // =========================
        // 2. DETECTOR WORKFLOW COUPLING
        // =========================
        String transcript = request.getTranscript();

        // Routed processing cleanly through our verified logic components
        List<String> evidence = evidenceExtractor.extract(transcript);
        List<String> gaps = gapDetector.detect(transcript);
        List<String> biases = biasDetector.detect(transcript);
        List<String> followUps = generateFollowUps(gaps);

        // =========================
        // 3. STABLE CONFIDENCE ENGINE
        // =========================
        // Swapped out manually nested math with centralized AnalysisMetricsCalculator metrics formulas
        double confidence = metricsCalculator.confidence(evidence, gaps, score);
        double dependencyRisk = metricsCalculator.dependencyRisk(evidence, gaps);
        double survivability = metricsCalculator.survivability(score, dependencyRisk);

        // =========================
        // 4. DTO CONSTRUCTION
        // =========================
        ScoreDTO scoreDTO = new ScoreDTO(
                "Transcript Evaluation",
                score,
                sentiment
        );

        List<EvidenceDTO> evidenceDTOs = evidence.stream()
                .map(e -> new EvidenceDTO("Evidence Point", e))
                .toList();

        List<KPIMappingDTO> kpis = List.of(
                new KPIMappingDTO("Confidence Engine", "Confidence: " + confidence),
                new KPIMappingDTO("Dependency Risk Engine", "Risk: " + dependencyRisk),
                new KPIMappingDTO("Survivability Engine", "Survivability: " + survivability)
        );

        List<String> errors = new ArrayList<>(gaps);

        List<String> recommendations = new ArrayList<>(followUps);
        biases.forEach(b -> recommendations.add("Bias Mitigation: " + b));

        // =========================
        // 5. RESPONSE
        // =========================
        return new AnalysisResponseDTO(
                summary,
                List.of(scoreDTO),
                evidenceDTOs,
                kpis,
                errors,
                recommendations
        );
    }

    // =====================================================
    // Follow-ups Generator
    // =====================================================
    private List<String> generateFollowUps(List<String> gaps) {
        List<String> list = new ArrayList<>();
        for (String g : gaps) {
            list.add("Clarify: " + g);
        }
        return list;
    }

    // =====================================================
    // Utility
    // =====================================================
    private int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
}