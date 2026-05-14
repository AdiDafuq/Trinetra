package com.trinetra.dto.core;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class AnalysisMetricsCalculator {

    public double confidence(List<?> evidence, List<?> gaps, int score) {
        int e = evidence == null ? 0 : evidence.size();
        int g = gaps == null ? 0 : gaps.size();

        double value = (e * 0.4) + (score * 0.01) - (g * 0.3);
        return clamp(value, 0.0, 1.0);
    }

    public double dependencyRisk(List<?> evidence, List<?> gaps) {
        int e = evidence == null ? 0 : evidence.size();
        int g = gaps == null ? 0 : gaps.size();

        double risk = (g * 0.4) + (Math.max(0, 5 - e) * 0.3);
        return clamp(risk, 0.0, 1.0);
    }

    public double survivability(int score, double dependencyRisk) {
        double base = score / 100.0;
        return clamp(base - dependencyRisk, 0.0, 1.0);
    }

    private double clamp(double v, double min, double max) {
        return Math.max(min, Math.min(max, v));
    }
}