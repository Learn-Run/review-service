package com.unionclass.reviewservice.domain.review.dto.in;

import com.unionclass.reviewservice.domain.review.entity.Review;
import com.unionclass.reviewservice.domain.review.vo.UpdateContentsReqVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateContentsReqDto {

    private String reviewerUuid;
    private String postUuid;
    private String contents;

    @Builder
    public UpdateContentsReqDto(String reviewerUuid, String postUuid, String contents) {
        this.reviewerUuid = reviewerUuid;
        this.postUuid = postUuid;
        this.contents = contents;
    }

    public static UpdateContentsReqDto of(String memberUuid, String postUuid, UpdateContentsReqVo updateContentsReqVo) {
        return UpdateContentsReqDto.builder()
                .reviewerUuid(memberUuid)
                .postUuid(postUuid)
                .contents(updateContentsReqVo.getContents())
                .build();
    }

    public Review toEntity(Review review) {
        return Review.builder()
                .id(review.getId())
                .reviewerUuid(review.getReviewerUuid())
                .revieweeUuid(review.getRevieweeUuid())
                .rating(review.getRating())
                .contents(contents)
                .post(review.getPost())
                .imageList(review.getImageList())
                .deleted(review.isDeleted())
                .deletedAt(review.getDeletedAt())
                .build();
    }
}
