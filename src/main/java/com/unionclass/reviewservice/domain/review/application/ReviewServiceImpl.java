package com.unionclass.reviewservice.domain.review.application;

import com.unionclass.reviewservice.client.post.application.PostServiceClient;
import com.unionclass.reviewservice.client.post.dto.GetPostInfoResDto;
import com.unionclass.reviewservice.common.exception.BaseException;
import com.unionclass.reviewservice.common.exception.ErrorCode;
import com.unionclass.reviewservice.domain.review.dto.in.CreateReviewReqDto;
import com.unionclass.reviewservice.domain.review.entity.Image;
import com.unionclass.reviewservice.domain.review.entity.Post;
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
    private final PostServiceClient postServiceClient;
    private final Imagefactory imagefactory;

    @Transactional
    @Override
    public void createReview(CreateReviewReqDto createReviewReqDto) {

        try {
            GetPostInfoResDto getPostInfoResDto = postServiceClient.getPostInfo(createReviewReqDto.getPostUuid());
            log.info("Post 조회 성공 - postUuid: {}", getPostInfoResDto.getPostUuid());

            reviewRepository.save(createReviewReqDto.toEntity(
                    imagefactory.createImages(createReviewReqDto.getImageList()), getPostInfoResDto));
            log.info("Post 에 대한 리뷰 생성 성공 - postUuid: {}, reviewerUuid: {}",
                    getPostInfoResDto.getPostUuid(), createReviewReqDto.getReviewerUuid());

        } catch (FeignException e) {
            log.warn("post-service 와의 통신 실패 - postUuid: {}", createReviewReqDto.getPostUuid());
            throw new BaseException(ErrorCode.POST_SERVICE_COMMUNICATION_FAILED);
        } catch (Exception e) {
            log.warn("리뷰 생성 중 알 수 없는 오류 발생 - postUuid: {}, reviewerUuid: {}, message: {}",
                    createReviewReqDto.getPostUuid(), createReviewReqDto.getReviewerUuid(), e.getMessage());
            throw new BaseException(ErrorCode.FAILED_TO_CREATE_REVIEW);
        }
    }
}
