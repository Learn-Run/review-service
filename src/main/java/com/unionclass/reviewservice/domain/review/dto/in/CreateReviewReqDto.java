package com.unionclass.reviewservice.domain.review.dto.in;

import com.unionclass.reviewservice.client.post.dto.GetPostInfoResDto;
import com.unionclass.reviewservice.domain.review.entity.Image;
import com.unionclass.reviewservice.domain.review.entity.Post;
import com.unionclass.reviewservice.domain.review.entity.Review;
import com.unionclass.reviewservice.domain.review.enums.Rating;
import com.unionclass.reviewservice.domain.review.vo.CreateReviewReqVo;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreateReviewReqDto {

    private String reviewerUuid;
    private String postUuid;
    private Rating rating;
    private String contents;
    private List<ImageReqDto> imageList;

    @Builder
    public CreateReviewReqDto(
            String reviewerUuid, String postUuid, Rating rating, String contents, List<ImageReqDto> imageList
    ) {
        this.reviewerUuid = reviewerUuid;
        this.postUuid = postUuid;
        this.rating = rating;
        this.contents = contents;
        this.imageList = imageList;
    }

    public static CreateReviewReqDto of(String memberUuid, String postUuid, CreateReviewReqVo createReviewReqVo) {
        return CreateReviewReqDto.builder()
                .reviewerUuid(memberUuid)
                .postUuid(postUuid)
                .rating(createReviewReqVo.getRating())
                .contents(createReviewReqVo.getContents())
                .imageList(createReviewReqVo.getImageList())
                .build();
    }

    public Review toEntity(List<Image> images, GetPostInfoResDto getPostInfoResDto) {
        return Review.builder()
                .reviewerUuid(reviewerUuid)
                .revieweeUuid(getPostInfoResDto.getMemberUuid())
                .rating(rating)
                .contents(contents)
                .post(Post.from(getPostInfoResDto))
                .imageList(images)
                .deletedStatus(false)
                .build();
    }
}
