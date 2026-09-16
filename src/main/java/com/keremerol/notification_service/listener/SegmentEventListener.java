package com.keremerol.notification_service.listener;

import com.keremerol.notification_service.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SegmentEventListener {

    @Autowired
    private GeminiService geminiService;

    @KafkaListener(topics = "segment-events", groupId = "notification-group")
    public void listen(String message) {
        System.out.println("📩 Yeni bildirim event'i alındı: " + message);

        String suggestion = geminiService.generateCampaignSuggestion(message);
        System.out.println("🤖 AI Kampanya Önerisi: " + suggestion);
    }
}