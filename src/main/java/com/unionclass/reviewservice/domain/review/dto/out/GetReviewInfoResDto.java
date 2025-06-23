package com.unionclass.reviewservice.domain.review.dto.out;

import com.unionclass.reviewservice.domain.review.entity.Image;
import com.unionclass.reviewservice.domain.review.entity.Post;
import com.unionclass.reviewservice.domain.review.entity.Review;
import com.unionclass.reviewservice.domain.review.enums.Rating;
import com.unionclass.reviewservice.domain.review.vo.GetReviewInfoResVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class GetReviewInfoResDto {

    private String reviewId;
    private String reviewerUuid;
    private String revieweeUuid;
    private Rating rating;
    private String contents;
    private Post post;
    private List<Image> imageList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public GetReviewInfoResDto(
            String reviewId, String reviewerUuid, String revieweeUuid,
            Rating rating, String contents, Post post, List<Image> imageList,
            LocalDateTime createdAt, LocalDateTime updatedAt
    ) {
        this.reviewId = reviewId;
        this.reviewerUuid = reviewerUuid;
        this.revieweeUuid = revieweeUuid;
        this.rating = rating;
        this.contents = contents;
        this.post = post;
        this.imageList = imageList;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static GetReviewInfoResDto from(Review review) {
        return GetReviewInfoResDto.builder()
                .reviewId(review.getId())
                .reviewerUuid(review.getReviewerUuid())
                .revieweeUuid(review.getRevieweeUuid())
                .rating(review.getRating())
                .contents(review.getContents())
                .post(review.getPost())
                .imageList(review.getImageList())
                .createdAt(review.getCreatedAt())
                .updatedAt(review.getUpdatedAt())
                .build();
    }

    public GetReviewInfoResVo toVo() {
        return GetReviewInfoResVo.builder()
                .reviewId(reviewId)
                .reviewerUuid(reviewerUuid)
                .revieweeUuid(revieweeUuid)
                .rating(rating)
                .contents(contents)
                .post(post)
                .imageList(imageList)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
