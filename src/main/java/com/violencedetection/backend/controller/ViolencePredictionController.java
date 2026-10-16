package com.violencedetection.backend.controller;

import com.violencedetection.backend.dto.PredictionResponse;
import com.violencedetection.backend.service.ViolencePredictionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class ViolencePredictionController {

    private final ViolencePredictionService violencePredictionService;

    public ViolencePredictionController(ViolencePredictionService violencePredictionService) {
        this.violencePredictionService = violencePredictionService;
    }

    @PostMapping(value = "/predict",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PredictionResponse> predict(@RequestParam("file")MultipartFile file, Authentication authentication) throws IOException
    {
        if(file.isEmpty())
            return ResponseEntity.badRequest().build();

        PredictionResponse response=violencePredictionService.predict(file);

        return ResponseEntity.ok(response);

    }

}
