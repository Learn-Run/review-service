package com.unionclass.reviewservice.domain.memberreview.infrastructure;

import com.unionclass.reviewservice.domain.memberreview.entity.MemberReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberReviewRepository extends JpaRepository<MemberReview, Long> {

    Optional<MemberReview> findByMemberUuid(String memberUuid);
}
