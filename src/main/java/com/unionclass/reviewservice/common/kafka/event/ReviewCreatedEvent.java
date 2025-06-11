package com.unionclass.reviewservice.common.kafka.event;

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
}