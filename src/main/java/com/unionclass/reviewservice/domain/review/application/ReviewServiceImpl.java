package com.unionclass.reviewservice.domain.review.application;

import com.unionclass.reviewservice.client.post.application.CommunityServiceClient;
import com.unionclass.reviewservice.client.post.dto.out.GetPostInfoResDto;
import com.unionclass.reviewservice.client.post.vo.out.GetPostInfoResVo;
import com.unionclass.reviewservice.common.exception.BaseException;
import com.unionclass.reviewservice.common.exception.ErrorCode;
import com.unionclass.reviewservice.common.response.BaseResponseEntity;
import com.unionclass.reviewservice.domain.review.dto.in.CreateReviewReqDto;
import com.unionclass.reviewservice.domain.review.dto.in.UpdateReviewReqDto;
import com.unionclass.reviewservice.domain.review.factory.Imagefactory;
import com.unionclass.reviewservice.domain.review.infrastructure.ReviewRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CommunityServiceClient communityServiceClient;
    private final Imagefactory imagefactory;

    @Transactional
    @Override
    public void createReview(CreateReviewReqDto createReviewReqDto) {

        try {
            GetPostInfoResDto getPostInfoResDto = GetPostInfoResDto.from(
                    communityServiceClient.getPostInfo(createReviewReqDto.getPostUuid()).result());
            log.info("질문 조회 성공 - postUuid: {}", getPostInfoResDto.getPostUuid());

            reviewRepository.save(createReviewReqDto.toEntity(
                    imagefactory.createImages(createReviewReqDto.getImageList()), getPostInfoResDto));
            log.info("질문에 대한 리뷰 생성 성공 - postUuid: {}, reviewerUuid: {}",
                    getPostInfoResDto.getPostUuid(), createReviewReqDto.getReviewerUuid());

        } catch (FeignException e) {
            log.warn("community-service 와의 통신 실패 - postUuid: {}", createReviewReqDto.getPostUuid());
            throw new BaseException(ErrorCode.COMMUNITY_SERVICE_COMMUNICATION_FAILED);
        } catch (Exception e) {
            log.error("리뷰 생성 중 알 수 없는 오류 발생 - postUuid: {}, reviewerUuid: {}, message: {}",
                    createReviewReqDto.getPostUuid(), createReviewReqDto.getReviewerUuid(), e.getMessage());
            throw new BaseException(ErrorCode.FAILED_TO_CREATE_REVIEW);
        }
    }

    @Transactional
    @Override
    public void updateContents(UpdateReviewReqDto updateReviewReqDto) {

        try {
            reviewRepository.save(updateReviewReqDto.toEntity(
                    reviewRepository.findByPost_PostUuidAndReviewerUuid(
                            updateReviewReqDto.getPostUuid(),
                            updateReviewReqDto.getReviewerUuid()
                    ).orElseThrow(() -> new BaseException(ErrorCode.FAILED_TO_FIND_REVIEW))));

            log.info("리뷰 수정 성공 - postUuid: {}, reviewerUuid: {}",
                    updateReviewReqDto.getPostUuid(), updateReviewReqDto.getReviewerUuid());

        } catch (Exception e) {

            log.error("리뷰 수정 중 알 수 없는 오류 발생 - postUuid: {}, reviewerUuid: {}, message: {}",
                    updateReviewReqDto.getPostUuid(), updateReviewReqDto.getReviewerUuid(), e.getMessage());
        }
    }
}
