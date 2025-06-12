package com.unionclass.reviewservice.common.kafka.util;

import com.unionclass.reviewservice.common.kafka.event.ReviewCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    @Value("${spring.kafka.topics.review-created}")
    private String reviewCreatedTopic;

    private final KafkaTemplate<String, ReviewCreatedEvent> reviewCreatedEventKafkaTemplate;

    public void sendReviewCreatedEvent(ReviewCreatedEvent reviewCreatedEvent) {

        log.info("Kafka 메시지 전송 시작: {}", reviewCreatedEvent);

        CompletableFuture<SendResult<String, ReviewCreatedEvent>> future
                = reviewCreatedEventKafkaTemplate.send(reviewCreatedTopic , reviewCreatedEvent);

        future.whenComplete((result, ex) -> {
           if(ex != null) {
               log.error("Kafka 메시지 전송 실패: {}", reviewCreatedEvent, ex);
           } else {
               log.info("Kafka 메시지 전송 성공: offset={}, topic={}",
                       result.getRecordMetadata().offset(), result.getRecordMetadata().topic());
           }
        });
    }
}