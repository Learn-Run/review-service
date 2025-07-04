package com.unionclass.reviewservice.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    SUCCESS_CREATE_REVIEW("리뷰 생성에 성공하였습니다."),
    SUCCESS_UPDATE_REVIEW_CONTENTS("리뷰 내용 수정에 성공하였습니다."),
    SUCCESS_UPDATE_REVIEW_IMAGES("리뷰 이미지 수정에 성공하였습니다."),
    SUCCESS_DELETE_REVIEW("리뷰 삭제에 성공하였습니다."),
    SUCCESS_FIND_REVIEW("리뷰 단건 조회에 성공하였습니다."),
    SUCCESS_GET_MEMBER_REVIEW_RATING_AVG("회원 리뷰 평균 평점 조회에 성공하였습니다."),
    ;

    private final String message;
}
