package com.keremerol.notification_service.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SegmentEventListener {

    @KafkaListener(topics = "segment-events", groupId = "notification-group")
    public void listen(String message) {
        System.out.println("📩 Yeni bildirim event'i alındı: " + message);
        // Burada ileride: e-posta gönderme, AI'ya soru sorma gibi işlemler eklenecek
        sendNotification(message);
    }

    private void sendNotification(String message) {
        // Şimdilik simüle ediyoruz — gerçek bir bildirim sistemi olsa
        // burada e-posta/SMS/push notification servisi çağrılırdı
        System.out.println("✅ Bildirim gönderildi: " + message);
    }
}