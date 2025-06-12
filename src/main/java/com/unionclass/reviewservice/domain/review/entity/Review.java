package com.unionclass.reviewservice.domain.review.entity;

import com.unionclass.reviewservice.common.entity.BaseDocument;
import com.unionclass.reviewservice.domain.review.enums.Rating;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "review")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseDocument {

    @Id
    private String id;

    // 리뷰 작성자 UUID
    private String reviewerUuid;

    // 리뷰 대상자 UUID
    private String revieweeUuid;

    private Rating rating;
    private String contents;
    private Post post;
    private List<Image> imageList;

    private boolean deleted;
    private LocalDateTime deletedAt;

    @Builder
    public Review(
            String id, String reviewerUuid, String revieweeUuid, Rating rating,
            String contents, Post post, List<Image> imageList, boolean deleted, LocalDateTime deletedAt
    ) {
        this.id = id;
        this.reviewerUuid = reviewerUuid;
        this.revieweeUuid = revieweeUuid;
        this.rating = rating;
        this.contents = contents;
        this.post = post;
        this.imageList = imageList;
        this.deleted = deleted;
        this.deletedAt = deletedAt;
    }

    public void deleteReview() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
