package com.unionclass.reviewservice.domain.review.application;

import com.unionclass.reviewservice.client.post.application.PostServiceClient;
import com.unionclass.reviewservice.client.post.dto.out.GetPostInfoResDto;
import com.unionclass.reviewservice.common.exception.BaseException;
import com.unionclass.reviewservice.common.exception.ErrorCode;
import com.unionclass.reviewservice.common.kafka.event.ReviewCreatedEvent;
import com.unionclass.reviewservice.common.kafka.util.KafkaProducer;
import com.unionclass.reviewservice.domain.review.dto.in.*;
import com.unionclass.reviewservice.domain.review.dto.out.GetReviewInfoResDto;
import com.unionclass.reviewservice.domain.review.dto.out.UpdateImagesResDto;
import com.unionclass.reviewservice.domain.review.entity.Review;
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
    private final PostServiceClient postServiceClient;
    private final Imagefactory imagefactory;
    private final KafkaProducer kafkaProducer;

    @Transactional
    @Override
    public void createReview(CreateReviewReqDto createReviewReqDto) {

        try {
            GetPostInfoResDto getPostInfoResDto = GetPostInfoResDto.from(
                    postServiceClient.getPostInfo(createReviewReqDto.getPostUuid()).result());
            log.info("질문 조회 성공 - postUuid: {}", getPostInfoResDto.getPostUuid());

            Review review = reviewRepository.save(createReviewReqDto.toEntity(
                    imagefactory.createImages(createReviewReqDto.getImageList()), getPostInfoResDto));
            log.info("질문에 대한 리뷰 생성 성공 - postUuid: {}, reviewerUuid: {}",
                    getPostInfoResDto.getPostUuid(), createReviewReqDto.getReviewerUuid());

            kafkaProducer.sendReviewCreatedEvent(ReviewCreatedEvent.from(review));

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

    @Transactional
    @Override
    public void deleteReview(DeleteReviewDto deleteReviewDto) {

        try {
            Review review = reviewRepository.findByPost_PostUuidAndReviewerUuid(
                    deleteReviewDto.getPostUuid(),
                    deleteReviewDto.getReviewerUuid()
            ).orElseThrow(() -> new BaseException(ErrorCode.FAILED_TO_FIND_REVIEW));

            review.deleteReview();
            reviewRepository.save(review);

            log.info("리뷰 삭제 성공 - postUuid: {}, reviewerUuid: {}",
                    deleteReviewDto.getPostUuid(), deleteReviewDto.getReviewerUuid());

        } catch (Exception e) {
            log.warn("리뷰 삭제 중 알 수 없는 오류 발생 - postUuid: {}, reviewerUuid: {}, message: {}",
                    deleteReviewDto.getPostUuid(), deleteReviewDto.getReviewerUuid(), e.getMessage());
            throw new BaseException(ErrorCode.FAILED_TO_DELETE_REVIEW);
        }
    }

    @Override
    public GetReviewInfoResDto getReviewInfo(String reviewId) {
        try {

            return GetReviewInfoResDto.from(reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new BaseException(ErrorCode.FAILED_TO_FIND_REVIEW)));

        } catch (BaseException e) {

            throw e;

        } catch (Exception e) {

            log.warn("리뷰 단건 조회 중 알 수 없는 오류 발생 - reviewId: {}, message: {}", reviewId, e.getMessage());

            throw new BaseException(ErrorCode.REVIEW_LOOKUP_FAILED);

        }
    }
}
