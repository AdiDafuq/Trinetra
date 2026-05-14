package com.trinetra.service.ai;

import org.springframework.stereotype.Service;

@Service
public class ConfidenceCalibrationService {

    public double calibrate(double rawConfidence, int evidenceCount, int gapCount) {

        double adjusted = rawConfidence;

        // more evidence → more confidence
        adjusted += evidenceCount * 0.03;

        // more gaps → reduce confidence
        adjusted -= gapCount * 0.05;

        // clamp
        return Math.max(0.0, Math.min(1.0, adjusted));
    }

    public String confidenceLabel(double confidence) {
        if (confidence >= 0.75) return "HIGH";
        if (confidence >= 0.5) return "MEDIUM";
        return "LOW";
    }
}