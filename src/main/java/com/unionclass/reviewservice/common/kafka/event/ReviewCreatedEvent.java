package com.unionclass.reviewservice.common.kafka.event;

import com.unionclass.reviewservice.domain.review.entity.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewCreatedEvent {

    private String memberUuid;
    private String reviewId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public ReviewCreatedEvent(String memberUuid, String reviewId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.memberUuid = memberUuid;
        this.reviewId = reviewId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ReviewCreatedEvent from(Review review) {
        return ReviewCreatedEvent.builder()
                .memberUuid(review.getReviewerUuid())
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}