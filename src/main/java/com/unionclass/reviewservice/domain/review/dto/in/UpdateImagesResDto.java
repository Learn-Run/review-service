package com.unionclass.reviewservice.domain.review.dto.in;

import com.unionclass.reviewservice.domain.review.entity.Image;
import com.unionclass.reviewservice.domain.review.entity.Review;
import com.unionclass.reviewservice.domain.review.vo.UpdateImagesReqVo;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateImagesResDto {

    private String reviewerUuid;
    private String postUuid;
    private List<ImageReqDto> imageList;

    @Builder
    public UpdateImagesResDto(String reviewerUuid, String postUuid, List<ImageReqDto> imageList) {
        this.reviewerUuid = reviewerUuid;
        this.postUuid = postUuid;
        this.imageList = imageList;
    }

    public static UpdateImagesResDto of(String memberUuid, String postUuid, UpdateImagesReqVo updateImagesReqVo) {
        return UpdateImagesResDto.builder()
                .reviewerUuid(memberUuid)
                .postUuid(postUuid)
                .imageList(updateImagesReqVo.getImageList())
                .build();
    }

    public Review toEntity(Review review, List<Image> imageList) {
        return Review.builder()
                .id(review.getId())
                .reviewerUuid(review.getReviewerUuid())
                .revieweeUuid(review.getRevieweeUuid())
                .rating(review.getRating())
                .contents(review.getContents())
                .imageList(imageList)
                .deleted(review.isDeleted())
                .deletedAt(review.getDeletedAt())
                .build();
    }
}
