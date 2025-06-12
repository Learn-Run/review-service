package com.unionclass.reviewservice.domain.review.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.unionclass.reviewservice.common.exception.BaseException;
import com.unionclass.reviewservice.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageType {

    IMAGE_TYPE_JPG("jpg"),
    IMAGE_TYPE_JPEG("jpeg"),
    IMAGE_TYPE_PNG("png"),
    IMAGE_TYPE_GIF("gif"),
    IMAGE_TYPE_WEBP("webp"),
    IMAGE_TYPE_SVG("svg"),
    IMAGE_TYPE_HEIC("heic")
    ;

    private final String imageType;

    @JsonValue
    public String getImageType() {
        return imageType;
    }

    @JsonCreator
    public static ImageType fromString(String value) {
        for (ImageType imageType : ImageType.values()) {
            if (imageType.imageType.equalsIgnoreCase(value)) {
                return imageType;
            }
        }
        throw new BaseException(ErrorCode.INVALID_IMAGE_TYPE);
    }
}
