package com.unionclass.reviewservice.domain.memberreview.presentation;

import com.unionclass.reviewservice.common.response.BaseResponseEntity;
import com.unionclass.reviewservice.common.response.ResponseMessage;
import com.unionclass.reviewservice.domain.memberreview.application.MemberReviewService;
import com.unionclass.reviewservice.domain.memberreview.vo.out.GetMemberReviewRatingAvgResVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member-review")
@Tag(name = "member-review")
public class MemberReviewController {

    private final MemberReviewService memberReviewService;

    /**
     * /api/v1/member/review
     *
     * 1. 회원 리뷰 평균 평점 조회
     */

    /**
     * 1. 회원 리뷰 평균 평점 조회
     *
     * @param memberUuid
     * @return
     */
    @Operation(
            summary = "회원 리뷰 평균 평점 조회",
            description = """
                    특정 회원이 받은 모든 리뷰의 평균 평점을 조회하는 API 입니다.
                    평균은 소수점 둘째 자리에서 반올림되어 소수점 첫째자리까지 표현해 반환됩니다.
                    
                    [요청 경로]
                    - memberUuid (String)
                    
                    [응답 필드]
                    - ratingAvg : 평균 평점
                    
                    [처리 방식]
                    - 회원 UUID 로 집계된 리뷰 정보 조회
                    - 평균 평점은 소수점 둘째 자리에서 반올림되어 소수점 첫째자리까지 표현해 반환
                    
                    [예외 상황]
                    - FAILED_TO_FIND_MEMBER_REVIEW : 해당 UUID 에 대한 리뷰 정보를 찾을 수 없는 경우
                    """
    )
    @GetMapping("/{memberUuid}/rating/average")
    public BaseResponseEntity<GetMemberReviewRatingAvgResVo> getMemberReviewRatingAvg(
            @PathVariable String memberUuid
    ) {
        return new BaseResponseEntity<>(
                ResponseMessage.SUCCESS_GET_MEMBER_REVIEW_RATING_AVG.getMessage(),
                memberReviewService.getMemberReviewRatingAvg(memberUuid).toVo()
        );
    }


}
