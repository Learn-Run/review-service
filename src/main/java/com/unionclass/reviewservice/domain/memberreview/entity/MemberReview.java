package com.unionclass.reviewservice.domain.memberreview.entity;

import com.unionclass.reviewservice.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_review")
public class MemberReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("회원 리뷰 UUID")
    @Column(nullable = false, unique = true)
    private Long uuid;

    @Comment("회원 UUID")
    @Column(nullable = false, unique = true, length = 36)
    private String memberUuid;

    @Comment("총 리뷰 개수")
    @Column(nullable = false)
    private long totalReviewCount;

    @Comment("총 리뷰 평점 합계")
    @Column(nullable = false)
    private long totalReviewRatingSum;

    @Comment("평균 리뷰 평점")
    @Column(nullable = false)
    private double reviewRatingAverage;

    @Comment("삭제 일시")
    private LocalDateTime deletedAt;

    @Comment("삭제 여부")
    @Column(nullable = false)
    private boolean deleted;

    @Builder
    public MemberReview(
            Long id, Long uuid, String memberUuid, long totalReviewCount, long totalReviewRatingSum,
            double reviewRatingAverage, LocalDateTime deletedAt, boolean deleted
    ) {
        this.id = id;
        this.uuid = uuid;
        this.memberUuid = memberUuid;
        this.totalReviewCount = totalReviewCount;
        this.totalReviewRatingSum = totalReviewRatingSum;
        this.reviewRatingAverage = reviewRatingAverage;
        this.deletedAt = deletedAt;
        this.deleted = deleted;
    }

    public static MemberReview of(Long uuid, String memberUuid) {
        return MemberReview.builder()
                .uuid(uuid)
                .memberUuid(memberUuid)
                .totalReviewCount(0L)
                .totalReviewRatingSum(0L)
                .reviewRatingAverage(0.0)
                .deleted(false)
                .build();
    }

    public void updateReviewStats(Long totalReviewCount, Long totalReviewRatingSum, double reviewRatingAverage) {
        this.totalReviewCount = totalReviewCount;
        this.totalReviewRatingSum = totalReviewRatingSum;
        this.reviewRatingAverage = reviewRatingAverage;
    }
}
