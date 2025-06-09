package com.unionclass.reviewservice.domain.review.vo;

import com.unionclass.reviewservice.domain.review.dto.in.ImageReqDto;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdateImagesReqVo {

    private List<ImageReqDto> imageList;
}