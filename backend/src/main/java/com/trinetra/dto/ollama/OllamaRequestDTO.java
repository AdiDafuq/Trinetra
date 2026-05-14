package com.trinetra.dto.ollama;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OllamaRequestDTO {

    private String model;
    private String prompt;
    private boolean stream;
    private Integer num_predict;
}