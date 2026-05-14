package com.trinetra.controller;

import com.trinetra.dto.request.TranscriptRequestDTO;
import com.trinetra.dto.response.AnalysisResponseDTO;
import com.trinetra.service.AnalysisService;
import com.trinetra.service.ai.OllamaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allows your frontend connection to hit the backend smoothly
public class AnalyzeController {

    private final AnalysisService analysisService;
    private final OllamaService ollamaService;

    @Autowired
    public AnalyzeController(
            AnalysisService analysisService,
            OllamaService ollamaService
    ) {
        this.analysisService = analysisService;
        this.ollamaService = ollamaService;
    }

    @PostMapping("/analyze")
    public AnalysisResponseDTO analyzeTranscript(@RequestBody TranscriptRequestDTO request) {
        // Standardized to match the core AnalysisPipelineService processing method
        return analysisService.analyze(request);
    }

    @GetMapping("/test-ollama")
    public String testOllama() {
        return ollamaService.analyzeWithModel(
                "Explain Spring Boot in one short sentence."
        );
    }
}
