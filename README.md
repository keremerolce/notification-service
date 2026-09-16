# Notification Service

Campaign Platform'dan yayınlanan segment değişikliği event'lerini Apache Kafka üzerinden dinleyen ve işleyen bir mikroservis. Event-driven mimarinin tüketici (consumer) tarafını temsil eder.

## Özellikler

- Kafka Consumer: segment-events topic'ini gerçek zamanlı dinler
- Bildirim İşleme: Yeni event geldiğinde bildirim gönderme sürecini simüle eder
- Bağımsız Servis: Campaign Platform'dan tamamen ayrı, kendi portunda çalışan bağımsız bir mikroservis

## Teknoloji Yığını

- Java 25 / Spring Boot 4.1.1
- Spring for Apache Kafka
- Maven

## Mimari

Bu servis, event-driven mikroservis mimarisinin tüketici tarafıdır:

[Campaign Platform :8082] → [Kafka: segment-events topic] → [Notification Service :8083]

Campaign Platform'da bir müşteri oluşturulup segmentine otomatik atama yapıldığında, bu servis event'i anlık olarak yakalar ve işler.

İlişkili proje: campaign-platform (https://github.com/keremerolce/campaign-platform)

## Kurulum ve Çalıştırma

Gereksinimler: JDK 25+, Docker Desktop (Kafka için), campaign-platform'un Kafka broker'ının ayakta olması (Docker Compose ile)

Adımlar:
1. campaign-platform reposundaki docker-compose.yml ile Kafka'nın çalıştığından emin ol
2. Uygulamayı çalıştır: ./mvnw spring-boot:run
3. Uygulama http://localhost:8083 üzerinde ayağa kalkar ve otomatik olarak segment-events topic'ini dinlemeye başlar

## Nasıl Çalışır

campaign-platform üzerinden yeni bir müşteri oluşturulduğunda (POST /api/customers), bu servis konsola şu şekilde log basar:

📩 Yeni bildirim event'i alındı: Müşteri 'Ahmet Yılmaz' segmentine atandı: Gold
✅ Bildirim gönderildi: Müşteri 'Ahmet Yılmaz' segmentine atandı: Gold

## Yol Haritası

- [x] Kafka consumer entegrasyonu
- [ ] Gerçek bildirim gönderimi (e-posta/SMS)
- [ ] AI destekli kişiselleştirilmiş kampanya önerisi (OpenAI/Spring AI entegrasyonu)
- [ ] Gelen event'lerin veritabanına kaydedilmesi (bildirim geçmişi)
