package com.unionclass.reviewservice.domain.review.entity;

import com.unionclass.reviewservice.client.post.dto.GetPostInfoResDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Post {

    private String postUuid;
    private String postTitle;

    @Builder
    public Post(String postUuid, String postTitle) {
        this.postUuid = postUuid;
        this.postTitle = postTitle;
    }

    public static Post from(GetPostInfoResDto dto) {
        return Post.builder()
                .postUuid(dto.getPostUuid())
                .postTitle(dto.getPostTitle())
                .build();
    }
}
