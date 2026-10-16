package com.violencedetection.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PredictionResponse {

    private String label;

    @JsonProperty("is_violent")
    private boolean isViolent;

    private double confidence;

    @JsonProperty("violence_probability")
    private double violenceProbability;

}