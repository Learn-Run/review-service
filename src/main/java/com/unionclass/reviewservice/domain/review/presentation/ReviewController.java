package com.unionclass.reviewservice.domain.review.presentation;

import com.unionclass.reviewservice.common.response.BaseResponseEntity;
import com.unionclass.reviewservice.common.response.ResponseMessage;
import com.unionclass.reviewservice.domain.review.application.ReviewService;
import com.unionclass.reviewservice.domain.review.dto.in.CreateReviewReqDto;
import com.unionclass.reviewservice.domain.review.dto.in.DeleteReviewDto;
import com.unionclass.reviewservice.domain.review.dto.in.UpdateContentsReqDto;
import com.unionclass.reviewservice.domain.review.dto.out.UpdateImagesResDto;
import com.unionclass.reviewservice.domain.review.vo.CreateReviewReqVo;
import com.unionclass.reviewservice.domain.review.vo.GetReviewInfoResVo;
import com.unionclass.reviewservice.domain.review.vo.UpdateContentsReqVo;
import com.unionclass.reviewservice.domain.review.vo.UpdateImagesReqVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/review")
@Tag(name = "review")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * /api/v1/review
     *
     * 1. 리뷰 생성
     * 2. 리뷰 내용 수정
     * 3. 리뷰 이미지 수정
     * 4. 리뷰 삭제
     * 5. 리뷰 단건 조회
     */

    /**
     * 1. 리뷰 생성
     *
     * @param memberUuid
     * @param postUuid
     * @param createReviewReqVo
     * @return
     */
    @Operation(
            summary = "리뷰 생성",
            description = """
                    질문에 대한 답변을 받고 그에 대한 리뷰를 작성하는 API 입니다.
                    
                    [요청 헤더]
                    - X-Member-UUID : (String) 현재 리뷰를 작성하는 사용자 UUID
                    
                    [요청 경로]
                    - postUuid : (String) 리뷰 대상 게시글 UUID
                    
                    [요청 바디]
                    - rating : (Rating) 필수 입력, 리뷰 평점 (1.0 ~ 5.0, 0.5 단위)
                    - contents : (String) 필수 입력, 리뷰 내용
                    - imageList : (List<ImageReqDto>) 선택 입력, 첨부 이미지 목록
                        - type : (String) 이미지 타입 (jpg, jpeg, png, gif, webp, svg, heic)
                        - imageUrl : (String) 이미지 경로
                    
                    [처리 로직]
                    - 커뮤니티 서비스(community-service)와 통신하여 게시글 정보 조회
                    - 게시글에 대한 리뷰 생성 및 이미지 연관 저장
                    
                    [예외 상황]
                    - COMMUNITY_SERVICE_COMMUNICATION_FAILED : community-service 와의 통신 실패
                    - FAILED_TO_CREATE_REVIEW : 알 수 없는 오류로 인해 리뷰 생성 실패
                    - INVALID_RATING_VALUE : 유효하지 않은 평점 값
                    """
    )
    @PostMapping("/{postUuid}")
    public BaseResponseEntity<Void> createReview(
            @RequestHeader("X-Member-UUID") String memberUuid,
            @PathVariable String postUuid,
            @Valid @RequestBody CreateReviewReqVo createReviewReqVo
    ) {
        reviewService.createReview(CreateReviewReqDto.of(memberUuid, postUuid, createReviewReqVo));
        return new BaseResponseEntity<>(ResponseMessage.SUCCESS_CREATE_REVIEW.getMessage());
    }

    /**
     * 2. 리뷰 내용 수정
     *
     * @param memberUuid
     * @param postUuid
     * @param updateContentsReqVo
     * @return
     */
    @Operation(
            summary = "리뷰 내용 수정",
            description = """
                    작성한 리뷰의 내용(contents)를 수정하는 API 입니다.
                    
                    [요청 헤더]
                    - X-Member-UUID : (String) 리뷰 작성자 UUID
                    
                    [요청 경로]
                    - postUuid : (String) 리뷰 대상 질문 UUID
                    
                    [요청 바디]
                    - contents : (String) 필수 입력, 수정할 리뷰 내용
                    
                    [처리 로직]
                    - postUuid 와 reviewUuid 를 기준으로 기존 리뷰 조회
                    - 해당 리뷰의 내용을 새로운 값으로 갱신
                    
                    [예외 상황]
                    - FAILED_TO_FIND_REVIEW : 해당 postUuid 와 reviewerUuid 조합의 리뷰를 찾지 못한 경우
                    - FAILED_TO_UPDATE_REVIEW_CONTENTS : 알 수 없는 오류로 인해 리뷰 내용 수정 실패
                    """
    )
    @PutMapping("/{postUuid}/contents")
    public BaseResponseEntity<Void> updateContents(
            @RequestHeader("X-Member-UUID") String memberUuid,
            @PathVariable String postUuid,
            @Valid @RequestBody UpdateContentsReqVo updateContentsReqVo
    ) {
        reviewService.updateContents(UpdateContentsReqDto.of(memberUuid, postUuid, updateContentsReqVo));
        return new BaseResponseEntity<>(ResponseMessage.SUCCESS_UPDATE_REVIEW_CONTENTS.getMessage());
    }

    /**
     * 3. 리뷰 이미지 수정
     *
     * @param memberUuid
     * @param postUuid
     * @param updateImagesReqVo
     * @return
     */
    @Operation(
            summary = "리뷰 이미지 수정",
            description = """
                    작성한 리뷰의 이미지 리스트(imageList)를 수정하는 API 입니다.
                    
                    [요청 헤더]
                    - X-Member-UUID : (String) 리뷰 작성자 UUID
                    
                    [요청 경로]
                    - postUuid : (String) 리뷰 대상 질문 UUID
                    
                    [요청 바디]
                    - imageList : (List<ImageReqDto>) 선택 입력, 새로운 첨부 이미지 목록
                        - type : (String) 이미지 타입 (jpg, jpeg, png, gif, webp, svg, heic)
                        - imageUrl : (String) 이미지 경로
                    
                    [주의 사항]
                    - 이미지 수정은 전체 교체 방식으로 처리되기 때문에
                      imageList 를 비워두고 API 를 요청하면, 기존 첨부 이미지가 모두 삭제됩니다.
                    
                    [처리 로직]
                    - postUuid 와 reviewerUuid 를 기준으로 기존 리뷰를 조회
                    - 리뷰에 첨부된 이미지 목록을 새롭게 입력 받은 이미지 목록으로 교체 후 저장
                    
                    [예외 상황]
                    - FAILED_TO_FIND_REVIEW : 해당 postUuid 와 reviewerUuid 조합의 리뷰를 찾지 못한 경우
                    - FAILED_TO_UPDATE_REVIEW_IMAGES : 알 수 없는 오류로 인해 리뷰 이미지 수정 실패
                    """
    )
    @PutMapping("/{postUuid}/images")
    public BaseResponseEntity<Void> updateImages(
            @RequestHeader("X-Member-UUID") String memberUuid,
            @PathVariable String postUuid,
            @Valid @RequestBody UpdateImagesReqVo updateImagesReqVo
    ) {
        reviewService.updateImages(UpdateImagesResDto.of(memberUuid, postUuid, updateImagesReqVo));
        return new BaseResponseEntity<>(ResponseMessage.SUCCESS_UPDATE_REVIEW_IMAGES.getMessage());
    }

    /**
     * 4. 리뷰 삭제
     *
     * @param memberUuid
     * @param postUuid
     * @return
     */
    @Operation(
            summary = "리뷰 삭제",
            description = """
                    사용자가 작성한 리뷰를 삭제하는 API 입니다.
                    이 API 는 실제 DB 에서 데이터를 제거하는 것이 아닌, **소프트 딜리트 방식**으로 처리됩니다.
                    deleted 값을 true 로 변경하고 deletedAt 시간을 기록합니다.
                    
                    [요청 헤더]
                    - X-Member-UUID : (String) 리뷰 작성자 UUID
            
                    [요청 경로]
                    - postUuid : (String) 리뷰 대상 질문 UUID
            
                    [처리 방식]
                    - postUuid 와 reviewerUuid 를 기준으로 리뷰 조회
                    - 해당 리뷰의 삭제 상태를 true 로 변경 (소프트 딜리트)
            
                    [예외 상황]
                    - FAILED_TO_FIND_REVIEW : 해당 postUuid 와 reviewerUuid 조합으로 조회한 리뷰가 존재하지 않는 경우
                    - FAILED_TO_DELETE_REVIEW : 알 수 없는 오류로 인해 리뷰 삭제 실패
                    """
    )
    @DeleteMapping("/{postUuid}")
    public BaseResponseEntity<Void> deleteReview(
            @RequestHeader("X-Member-UUID") String memberUuid,
            @PathVariable String postUuid
    ) {
        reviewService.deleteReview(DeleteReviewDto.of(memberUuid, postUuid));
        return new BaseResponseEntity<>(ResponseMessage.SUCCESS_DELETE_REVIEW.getMessage());
    }

    /**
     * 5. 리뷰 단건 조회
     *
     * @param reviewId
     * @return
     */
    @Operation(
            summary = "리뷰 ID 로 리뷰 단건 조회",
            description = """
                    리뷰 ID(reviewId)를 기준으로 단일 리뷰 정보를 조회하는 API 입니다.
                    
                    [요청 경로]
                    - reviewId : (String) 조회할 리뷰의 ID
                    
                    [응답 필드]
                    - reviewId : (String) 리뷰 ID (UUID 형식)
                    - reviewerUuid : (String) 리뷰 작성자 UUID
                    - revieweeUuid : (String) 리뷰 대상자 UUID
                    - rating : (Rating) 리뷰 평점 (1.0 ~ 5.0, 0.5 단위)
                    - contents : (String) 리뷰 내용
                    - post : 리뷰 대상 질문
                        - postUuid : (String) 질문 UUID
                        - postTitle : (String) 질문 제목
                    - imageList : (List<ImageReqDto>) 첨부 이미지 목록
                        - type : (String) 이미지 타입 (jpg, jpeg, png, gif, webp, svg, heic)
                        - imageUrl : (String) 이미지 경로
                        - alt : (String) 이미지 대체 텍스트 ("리뷰에 대한 ()번째 첨부 이미지입니다." 의 형태)
                    - createdAt : (LocalDateTime) 리뷰 생성일시
                    - updatedAt : (LocalDateTime) 리뷰 수정일시
                    
                    [처리 로직]
                    - 리뷰 ID로 DB 에서 리뷰 정보를 조회 후 결과를 VO 로 반환
                   
                    [예외 상황]
                    - FAILED_TO_FIND_REVIEW : 해당 리뷰 ID로 리뷰를 찾을 수 없는 경우
                    - REVIEW_LOOKUP_FAILED : 알 수 없는 오류로 인해 리뷰 단건 조회 실패
                    """
    )
    @GetMapping("/info/{reviewId}")
    public BaseResponseEntity<GetReviewInfoResVo> getReviewInfo(
            @PathVariable String reviewId
    ) {
        return new BaseResponseEntity<>(
                ResponseMessage.SUCCESS_FIND_REVIEW.getMessage(),
                reviewService.getReviewInfo(reviewId).toVo()
        );
    }
}
