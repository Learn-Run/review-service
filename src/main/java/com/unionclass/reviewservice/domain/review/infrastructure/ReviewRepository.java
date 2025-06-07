package com.unionclass.reviewservice.domain.review.infrastructure;

import com.unionclass.reviewservice.domain.review.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, String> {
}
