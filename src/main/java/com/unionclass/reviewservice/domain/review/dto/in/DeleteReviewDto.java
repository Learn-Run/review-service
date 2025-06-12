package com.unionclass.reviewservice.domain.review.dto.in;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteReviewDto {

    private String reviewerUuid;
    private String postUuid;

    @Builder
    public DeleteReviewDto(String reviewerUuid, String postUuid) {
        this.reviewerUuid = reviewerUuid;
        this.postUuid = postUuid;
    }

    public static DeleteReviewDto of(String memberUuid, String postUuid) {
        return DeleteReviewDto.builder()
                .reviewerUuid(memberUuid)
                .postUuid(postUuid)
                .build();
    }
}
