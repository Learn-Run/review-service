package com.unionclass.reviewservice.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage {

    SUCCESS_CREATE_REVIEW("리뷰 생성에 성공하였습니다."),
    SUCCESS_UPDATE_REVIEW("리뷰 내용 수정에 성공하였습니다."),
    ;

    private final String message;
}
