package com.unionclass.reviewservice.domain.review.dto.in;

import com.unionclass.reviewservice.domain.review.enums.ImageType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ImageReqDto {

    private ImageType type;
    private String imageUrl;

    @Builder
    public ImageReqDto(ImageType type, String imageUrl) {
        this.type = type;
        this.imageUrl = imageUrl;
    }
}
