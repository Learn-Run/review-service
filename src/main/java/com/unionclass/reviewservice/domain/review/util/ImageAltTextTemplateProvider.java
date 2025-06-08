package com.unionclass.reviewservice.domain.review.util;

import org.springframework.stereotype.Component;

@Component
public class ImageAltTextTemplateProvider {

    public String getProfileImageAltTextTemplate(String nickname) {
        return nickname + " 회원님의 프로필 이미지입니다.";
    }

    public String getReviewImageAltTextTemplate(int index) {
        return "리뷰에 대한 " + index + "번째 첨부 이미지입니다.";
    }
}
