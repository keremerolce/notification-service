package com.keremerol.notification_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String URL_TEMPLATE =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.5-flash-lite:generateContent";

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateCampaignSuggestion(String customerInfo) {
        String prompt = "Sen bir banka kampanya uzmanısın. Şu müşteri profiline uygun, " +
                "kısa ve yaratıcı bir kampanya önerisi üret (2-3 cümle, Türkçe): " + customerInfo;

        Map<String, Object> requestBody = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(Map.of("text", prompt)))
                )
        );

        String url = URL_TEMPLATE + "?key=" + apiKey;

        try {
            Map response = restTemplate.postForObject(url, requestBody, Map.class);
            List candidates = (List) response.get("candidates");
            Map firstCandidate = (Map) candidates.get(0);
            Map content = (Map) firstCandidate.get("content");
            List parts = (List) content.get("parts");
            Map firstPart = (Map) parts.get(0);
            return (String) firstPart.get("text");
        } catch (Exception e) {
            return "AI önerisi alınamadı: " + e.getMessage();
        }
    }
}