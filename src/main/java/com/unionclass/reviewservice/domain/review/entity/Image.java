package com.unionclass.reviewservice.domain.review.entity;

import com.unionclass.reviewservice.domain.review.enums.ImageType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Image {

    private ImageType type;
    private String imageUrl;
    private String alt;

    @Builder
    public Image(ImageType type, String imageUrl, String alt) {
        this.type = type;
        this.imageUrl = imageUrl;
        this.alt = alt;
    }
}
