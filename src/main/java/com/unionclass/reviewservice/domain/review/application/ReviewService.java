package com.unionclass.reviewservice.domain.review.application;

import com.unionclass.reviewservice.domain.review.dto.in.CreateReviewReqDto;

public interface ReviewService {

    void createReview(CreateReviewReqDto createReviewReqDto);
}
