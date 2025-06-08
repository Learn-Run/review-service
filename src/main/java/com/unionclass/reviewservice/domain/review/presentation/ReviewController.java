package com.unionclass.reviewservice.domain.review.presentation;

import com.unionclass.reviewservice.domain.review.application.ReviewService;
import com.unionclass.reviewservice.domain.review.dto.in.CreateReviewReqDto;
import com.unionclass.reviewservice.domain.review.vo.CreateReviewReqVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
@Tag(name = "review")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * /api/v1/review
     *
     * 1. 리뷰 생성
     */

    @PostMapping("/{postUuid}")
    public Void createReview(
            @RequestHeader("X-Member-UUID") String memberUuid,
            @PathVariable String postUuid,
            @Valid @RequestBody CreateReviewReqVo createReviewReqVo
    ) {
        reviewService.createReview(CreateReviewReqDto.of(memberUuid, postUuid, createReviewReqVo));
    }
}
