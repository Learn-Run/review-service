package com.unionclass.reviewservice.client.post.dto;

import lombok.Getter;

@Getter
public class GetPostInfoResDto {

    private String memberUuid;
    private String postUuid;
    private String postTitle;
}
