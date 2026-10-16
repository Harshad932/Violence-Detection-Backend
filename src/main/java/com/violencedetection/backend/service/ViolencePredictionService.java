package com.violencedetection.backend.service;

import com.violencedetection.backend.dto.PredictionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ViolencePredictionService {

    private final RestTemplate restTemplate;
    private final String fastApiBaseUrl;

    public ViolencePredictionService(RestTemplate restTemplate,@Value("${fastapi.base-url}") String fastApiBaseUrl) {
        this.restTemplate = restTemplate;
        this.fastApiBaseUrl = fastApiBaseUrl;
    }

    public PredictionResponse predict(MultipartFile file) throws IOException
    {

        ByteArrayResource videoResource=new ByteArrayResource(file.getBytes()){
            @Override
            public String getFilename()
            {
                return file.getOriginalFilename()!=null? file.getOriginalFilename():"video.mp4";
            }
        };

        HttpHeaders fileHeader=new HttpHeaders();

        fileHeader.setContentType(MediaType.parseMediaType(
                file.getContentType()!=null?file.getContentType():"application/octet-stream"
        ));

        HttpEntity<ByteArrayResource> fileEntity=new HttpEntity<>(videoResource,fileHeader);

        MultiValueMap<String,Object> body=new LinkedMultiValueMap<>();

        body.add("file",fileEntity);

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String,Object>> request=new HttpEntity<>(body,headers);

        ResponseEntity<PredictionResponse> response=
                restTemplate.postForEntity(fastApiBaseUrl+"/predict",request,PredictionResponse.class);

        if(response.getBody()==null)
            throw new IllegalStateException("FastApi returned an empty prediction response");

        return response.getBody();

    }

}
