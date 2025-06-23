package com.unionclass.reviewservice.domain.review.dto.out;

import com.unionclass.reviewservice.domain.review.entity.Image;
import com.unionclass.reviewservice.domain.review.entity.Post;
import com.unionclass.reviewservice.domain.review.entity.Review;
import com.unionclass.reviewservice.domain.review.enums.Rating;
import com.unionclass.reviewservice.domain.review.vo.GetReviewInfoResVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    public GetReviewInfoResDto(
            String reviewId, String reviewerUuid, String revieweeUuid,
            Rating rating, String contents, Post post, List<Image> imageList
    ) {
        this.reviewId = reviewId;
        this.reviewerUuid = reviewerUuid;
        this.revieweeUuid = revieweeUuid;
        this.rating = rating;
        this.contents = contents;
        this.post = post;
        this.imageList = imageList;
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
                .build();
    }
}
