package com.trinetra.dto.response;

import java.util.List;

import com.trinetra.dto.core.ScoreDTO;
import com.trinetra.dto.core.EvidenceDTO;
import com.trinetra.dto.core.KPIMappingDTO;

public class AnalysisResponseDTO {

    private String summary;

    private List<ScoreDTO> scores;

    private List<EvidenceDTO> evidence;

    private List<KPIMappingDTO> kpiMappings;

    private List<String> gaps;

    private List<String> followUpQuestions;

    public AnalysisResponseDTO() {
    }

    public AnalysisResponseDTO(String summary,
                               List<ScoreDTO> scores,
                               List<EvidenceDTO> evidence,
                               List<KPIMappingDTO> kpiMappings,
                               List<String> gaps,
                               List<String> followUpQuestions) {
        this.summary = summary;
        this.scores = scores;
        this.evidence = evidence;
        this.kpiMappings = kpiMappings;
        this.gaps = gaps;
        this.followUpQuestions = followUpQuestions;
    }

    public String getSummary() {
        return summary;
    }

    public List<ScoreDTO> getScores() {
        return scores;
    }

    public List<EvidenceDTO> getEvidence() {
        return evidence;
    }

    public List<KPIMappingDTO> getKpiMappings() {
        return kpiMappings;
    }

    public List<String> getGaps() {
        return gaps;
    }

    public List<String> getFollowUpQuestions() {
        return followUpQuestions;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setScores(List<ScoreDTO> scores) {
        this.scores = scores;
    }

    public void setEvidence(List<EvidenceDTO> evidence) {
        this.evidence = evidence;
    }

    public void setKpiMappings(List<KPIMappingDTO> kpiMappings) {
        this.kpiMappings = kpiMappings;
    }

    public void setGaps(List<String> gaps) {
        this.gaps = gaps;
    }

    public void setFollowUpQuestions(List<String> followUpQuestions) {
        this.followUpQuestions = followUpQuestions;
    }
}