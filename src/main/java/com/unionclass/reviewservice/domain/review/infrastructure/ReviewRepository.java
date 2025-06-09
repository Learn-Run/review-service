package com.unionclass.reviewservice.domain.review.infrastructure;

import com.unionclass.reviewservice.domain.review.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ReviewRepository extends MongoRepository<Review, String> {

    Optional<Review> findByPost_PostUuidAndReviewerUuid(String postUuid, String reviewerUuid);
}
