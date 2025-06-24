package com.unionclass.reviewservice.domain.memberreview.application;

import com.unionclass.reviewservice.common.kafka.event.ReviewCreatedEvent;
import com.unionclass.reviewservice.domain.memberreview.dto.out.GetMemberReviewRatingAvgResDto;

public interface MemberReviewService {

    void aggregateMemberReview(ReviewCreatedEvent event);

    GetMemberReviewRatingAvgResDto getMemberReviewRatingAvg(String memberUuid);
}
