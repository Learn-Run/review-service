package com.unionclass.reviewservice.domain.memberreview.vo.out;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetMemberReviewRatingAvgResVo {

    private double ratingAvg;

    @Builder
    public GetMemberReviewRatingAvgResVo(double ratingAvg) {
        this.ratingAvg = ratingAvg;
    }
}
