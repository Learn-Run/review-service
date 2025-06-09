package com.unionclass.reviewservice.domain.review.application;

import com.unionclass.reviewservice.domain.review.dto.in.CreateReviewReqDto;
import com.unionclass.reviewservice.domain.review.dto.in.UpdateContentsReqDto;
import com.unionclass.reviewservice.domain.review.dto.in.UpdateImagesResDto;

public interface ReviewService {

    void createReview(CreateReviewReqDto createReviewReqDto);
    void updateContents(UpdateContentsReqDto updateContentsReqDto);
    void updateImages(UpdateImagesResDto updateImagesResDto);
}
