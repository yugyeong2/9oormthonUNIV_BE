package org.be._9oormthonuniv.global.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumerService {

    @KafkaListener(topics = "comments", groupId = "comment-group")
    public void listen(String message) {
        log.info("[Kafka] 수신 메시지: {}", message);
    }
}
