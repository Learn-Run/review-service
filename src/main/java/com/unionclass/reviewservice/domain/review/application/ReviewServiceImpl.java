package com.unionclass.reviewservice.domain.review.application;

import com.unionclass.reviewservice.client.post.application.CommunityServiceClient;
import com.unionclass.reviewservice.client.post.dto.out.GetPostInfoResDto;
import com.unionclass.reviewservice.common.exception.BaseException;
import com.unionclass.reviewservice.common.exception.ErrorCode;
import com.unionclass.reviewservice.domain.review.dto.in.CreateReviewReqDto;
import com.unionclass.reviewservice.domain.review.dto.in.UpdateContentsReqDto;
import com.unionclass.reviewservice.domain.review.dto.in.UpdateImagesResDto;
import com.unionclass.reviewservice.domain.review.entity.Image;
import com.unionclass.reviewservice.domain.review.entity.Review;
import com.unionclass.reviewservice.domain.review.factory.Imagefactory;
import com.unionclass.reviewservice.domain.review.infrastructure.ReviewRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public void updateContents(UpdateContentsReqDto updateContentsReqDto) {

        try {
            reviewRepository.save(
                    updateContentsReqDto.toEntity(
                            reviewRepository.findByPost_PostUuidAndReviewerUuid(
                                    updateContentsReqDto.getPostUuid(),
                                    updateContentsReqDto.getReviewerUuid()
                            ).orElseThrow(() -> new BaseException(ErrorCode.FAILED_TO_FIND_REVIEW))));

            log.info("리뷰 내용 수정 성공 - postUuid: {}, reviewerUuid: {}",
                    updateContentsReqDto.getPostUuid(), updateContentsReqDto.getReviewerUuid());

        } catch (Exception e) {
            log.warn("리뷰 내용 수정 중 알 수 없는 오류 발생 - postUuid: {}, reviewerUuid: {}, message: {}",
                    updateContentsReqDto.getPostUuid(), updateContentsReqDto.getReviewerUuid(), e.getMessage());
            throw new BaseException(ErrorCode.FAILED_TO_UPDATE_REVIEW_CONTENTS);
        }
    }

    @Transactional
    @Override
    public void updateImages(UpdateImagesResDto updateImagesResDto) {

        try {
            reviewRepository.save(updateImagesResDto.toEntity(
                    reviewRepository.findByPost_PostUuidAndReviewerUuid(
                            updateImagesResDto.getPostUuid(),
                            updateImagesResDto.getReviewerUuid()
                    ).orElseThrow(() -> new BaseException(ErrorCode.FAILED_TO_FIND_REVIEW)),
                    imagefactory.createImages(updateImagesResDto.getImageList())));

            log.info("리뷰 이미지 수정 성공 - postUuid: {}, reviewerUuid: {}",
                    updateImagesResDto.getPostUuid(), updateImagesResDto.getReviewerUuid());

        } catch (Exception e) {
            log.warn("리뷰 이미지 수정 중 알 수 없는 오류 발생 - postUuid: {}, reviewerUuid: {}, message: {}",
                    updateImagesResDto.getPostUuid(), updateImagesResDto.getReviewerUuid(), e.getMessage());
            throw new BaseException(ErrorCode.FAILED_TO_UPDATE_REVIEW_IMAGES);
        }
    }
}
