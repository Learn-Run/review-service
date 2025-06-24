package com.unionclass.reviewservice.common.kafka.util;

import com.unionclass.reviewservice.common.kafka.event.ReviewCreatedEvent;
import com.unionclass.reviewservice.domain.memberreview.application.MemberReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumer {

    private final MemberReviewService memberReviewService;

    @KafkaListener(
            topics = "review-created",
            groupId = "review-aggregate-group",
            containerFactory = "reviewCreatedEventListener"
    )
    public void consumeReviewEvent(
            ReviewCreatedEvent reviewCreatedEvent,
            ConsumerRecord<String, ReviewCreatedEvent> consumerRecord
    ) {
        log.info("리뷰 생성 이벤트 수신 완료: {}", reviewCreatedEvent);
        log.info("리뷰 생성 이벤트 수신 - topic: {}, partition: {}, offset: {}",
                consumerRecord.topic(), consumerRecord.partition(), consumerRecord.offset());
        memberReviewService.aggregateMemberReview(reviewCreatedEvent);
    }
}
