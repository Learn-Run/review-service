package com.unionclass.reviewservice.domain.memberreview.dto.out;

import com.unionclass.reviewservice.domain.memberreview.vo.out.GetMemberReviewRatingAvgResVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetMemberReviewRatingAvgResDto {

    private double ratingAvg;

    @Builder
    public GetMemberReviewRatingAvgResDto(double ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public static GetMemberReviewRatingAvgResDto from(double roundedRatingAvg) {
        return GetMemberReviewRatingAvgResDto.builder()
                .ratingAvg(roundedRatingAvg)
                .build();
    }

    public GetMemberReviewRatingAvgResVo toVo() {
        return GetMemberReviewRatingAvgResVo.builder()
                .ratingAvg(ratingAvg)
                .build();
    }
}
