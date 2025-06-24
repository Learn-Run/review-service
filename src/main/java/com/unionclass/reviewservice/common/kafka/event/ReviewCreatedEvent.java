package com.unionclass.reviewservice.common.kafka.event;

import com.unionclass.reviewservice.domain.review.entity.Review;
import com.unionclass.reviewservice.domain.review.enums.Rating;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReviewCreatedEvent {

    private String reviewerUuid;
    private String revieweeUuid;
    private String reviewId;
    private Double rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public ReviewCreatedEvent(
            String reviewerUuid, String revieweeUuid, String reviewId,
            Double rating, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.reviewerUuid = reviewerUuid;
        this.revieweeUuid = revieweeUuid;
        this.reviewId = reviewId;
        this.rating = rating;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ReviewCreatedEvent from(Review review) {
        return ReviewCreatedEvent.builder()
                .reviewerUuid(review.getReviewerUuid())
                .revieweeUuid(review.getRevieweeUuid())
                .reviewId(review.getId())
                .rating(review.getRating().getValue())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }
}