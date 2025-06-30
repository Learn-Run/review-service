package com.unionclass.reviewservice.domain.memberreview.application;

import com.unionclass.reviewservice.common.exception.BaseException;
import com.unionclass.reviewservice.common.exception.ErrorCode;
import com.unionclass.reviewservice.common.kafka.event.MemberCreatedEvent;
import com.unionclass.reviewservice.common.kafka.event.ReviewCreatedEvent;
import com.unionclass.reviewservice.common.util.NumericUuidGenerator;
import com.unionclass.reviewservice.domain.memberreview.dto.out.GetMemberReviewRatingAvgResDto;
import com.unionclass.reviewservice.domain.memberreview.entity.MemberReview;
import com.unionclass.reviewservice.domain.memberreview.infrastructure.MemberReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberReviewServiceImpl implements MemberReviewService {

    private final MemberReviewRepository memberReviewRepository;

    private final NumericUuidGenerator uuidGenerator;

    @Transactional
    @Override
    public void aggregateMemberReview(ReviewCreatedEvent event) {

        try {

            String memberUuid = event.getRevieweeUuid();
            int ratingAsInt = (int) (event.getRating() * 10);

            MemberReview memberReview = memberReviewRepository.findByMemberUuid(memberUuid)
                    .orElseThrow(() -> new BaseException(ErrorCode.FAILED_TO_FIND_MEMBER_REVIEW));

            long newCount = memberReview.getTotalReviewCount() + 1;
            long newSum = memberReview.getTotalReviewRatingSum() + ratingAsInt;
            double newAvg = (newSum / (double) newCount) / 10.0;

            memberReview.updateReviewStats(newCount, newSum, newAvg);

            memberReviewRepository.save(memberReview);

            log.info("리뷰 집계 완료 - memberUuid: {}, 작성 리뷰 평점: {}, 반영된 리뷰 평균 평점: {}",
                    memberUuid, event.getRating(), newAvg);

        } catch (Exception e) {

            log.warn("리뷰 집계 실패 - event: {}, message: {}", event, e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_AGGREGATE_REVIEW);

        }
    }

    @Override
    public GetMemberReviewRatingAvgResDto getMemberReviewRatingAvg(String memberUuid) {

        return GetMemberReviewRatingAvgResDto
                .from(Math.round(memberReviewRepository.findByMemberUuid(memberUuid)
                .orElseThrow(() -> new BaseException(ErrorCode.FAILED_TO_FIND_MEMBER_REVIEW))
                .getReviewRatingAverage() * 10) / 10.0);
    }

    @Transactional
    @Override
    public void initializeMemberReview(MemberCreatedEvent event) {

        try {

            memberReviewRepository.save(event.toEntity(uuidGenerator.generate()));

            log.info("회원 리뷰 초기화 성공 - memberUuid: {}", event.getMemberUuid());

        } catch (Exception e) {

            log.warn("회원 리뷰 초기화 실패 - memberUuid: {}, message: {}", event.getMemberUuid(), e.getMessage(), e);

            throw new BaseException(ErrorCode.FAILED_TO_INITIALIZE_MEMBER_REVIEW);
        }
    }
}
