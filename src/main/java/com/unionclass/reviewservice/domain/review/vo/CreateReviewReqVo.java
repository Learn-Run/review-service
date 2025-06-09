package com.unionclass.reviewservice.domain.review.vo;

import com.unionclass.reviewservice.domain.review.dto.in.ImageReqDto;
import com.unionclass.reviewservice.domain.review.enums.Rating;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateReviewReqVo {

    @NotNull(message = "평점은 필수 입력 항목입니다.")
    private Rating rating;

    @NotNull(message = "리뷰 내용은 필수 입력 항목입니다.")
    private String contents;
    private List<ImageReqDto> imageList;

}
