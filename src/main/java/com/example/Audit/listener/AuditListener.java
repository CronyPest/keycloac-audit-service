package com.example.Audit.listener;

import com.example.Audit.service.AuditService;
import com.example.avro.ActionRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditListener {

    private final AuditService service;

    @KafkaListener(topics = "${app.topic}", concurrency = "1")
    public void receiveMessage(ActionRecord message) {

        log.info("Старт получения сообщения из топика аудита: {}", message);
        service.createAudit(message);
    }
}
