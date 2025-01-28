package com.javapulses.deepSeek.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DeepSeekService {

    @Value("${deepseek.api.key}")
    private String apiKey;

    @Value("${deepseek.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public DeepSeekService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getResponse(String prompt) {
        try {
            // 1. Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            // 2. Build the request body (adjust based on DeepSeek API specs)
            String requestBody = String.format("""
                {
                    "model": "deepseek-chat",
                    "messages": [
                        {"role": "user", "content": "%s"}
                    ]
                }
                """, prompt);

            // 3. Send POST request
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            // 4. Check response status
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                throw new RuntimeException("API request failed: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to DeepSeek API: " + e.getMessage());
        }
    }
}