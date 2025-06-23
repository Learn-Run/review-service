package com.unionclass.reviewservice.domain.review.vo;

import com.unionclass.reviewservice.domain.review.entity.Image;
import com.unionclass.reviewservice.domain.review.entity.Post;
import com.unionclass.reviewservice.domain.review.enums.Rating;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class GetReviewInfoResVo {

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
    public GetReviewInfoResVo(
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
}
