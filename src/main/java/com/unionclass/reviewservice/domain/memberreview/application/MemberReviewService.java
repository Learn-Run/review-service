package com.unionclass.reviewservice.domain.memberreview.application;

import com.unionclass.reviewservice.common.kafka.event.ReviewCreatedEvent;

public interface MemberReviewService {

    void aggregateMemberReview(ReviewCreatedEvent event);
}
