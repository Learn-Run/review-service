package com.unionclass.reviewservice.domain.review.application;

import com.unionclass.reviewservice.domain.review.dto.in.*;

public interface ReviewService {

    void createReview(CreateReviewReqDto createReviewReqDto);
    void updateContents(UpdateContentsReqDto updateContentsReqDto);
    void updateImages(UpdateImagesResDto updateImagesResDto);
    void deleteReview(DeleteReviewDto deleteReviewDto);
    GetReviewInfoResDto getReviewInfo(String reviewId);
}
