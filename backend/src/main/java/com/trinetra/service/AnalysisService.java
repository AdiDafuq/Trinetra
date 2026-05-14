package com.trinetra.service;

import com.trinetra.dto.request.TranscriptRequestDTO;
import com.trinetra.dto.response.AnalysisResponseDTO;

public interface AnalysisService {

    AnalysisResponseDTO analyzeTranscript(TranscriptRequestDTO request);
}