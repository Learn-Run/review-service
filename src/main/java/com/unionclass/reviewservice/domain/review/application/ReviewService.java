package com.unionclass.reviewservice.domain.review.application;

import com.unionclass.reviewservice.domain.review.dto.in.CreateReviewReqDto;
import com.unionclass.reviewservice.domain.review.dto.in.UpdateReviewReqDto;

public interface ReviewService {

    void createReview(CreateReviewReqDto createReviewReqDto);
    void updateContents(UpdateReviewReqDto updateReviewReqDto);
}
