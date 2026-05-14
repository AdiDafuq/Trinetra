package com.trinetra.dto.request;

public class TranscriptRequestDTO {

    private String transcript;

    public TranscriptRequestDTO() {
    }

    public TranscriptRequestDTO(String transcript) {
        this.transcript = transcript;
    }

    public String getTranscript() {
        return transcript;
    }

    public void setTranscript(String transcript) {
        this.transcript = transcript;
    }
}