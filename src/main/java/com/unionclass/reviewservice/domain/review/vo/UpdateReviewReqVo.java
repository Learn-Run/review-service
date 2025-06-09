package com.unionclass.reviewservice.domain.review.vo;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateReviewReqVo {

    @NotNull(message = "리뷰 내용은 필수 입력 항목입니다.")
    private String contents;
}
