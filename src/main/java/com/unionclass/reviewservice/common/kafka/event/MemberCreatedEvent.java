package com.unionclass.reviewservice.common.kafka.event;

import com.unionclass.reviewservice.domain.memberreview.entity.MemberReview;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreatedEvent {

    private String memberUuid;
    private String nickname;

    @Builder
    public MemberCreatedEvent(String memberUuid, String nickname) {
        this.memberUuid = memberUuid;
        this.nickname = nickname;
    }

    public MemberReview toEntity(Long uuid) {

        return MemberReview.builder()
                .uuid(uuid)
                .memberUuid(memberUuid)
                .totalReviewCount(0L)
                .totalReviewRatingSum(0L)
                .reviewRatingAverage(0.0)
                .deleted(false)
                .build();
    }
}