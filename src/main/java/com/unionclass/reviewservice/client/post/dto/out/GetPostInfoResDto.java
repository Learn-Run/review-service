package com.unionclass.reviewservice.client.post.dto.out;

import com.unionclass.reviewservice.client.post.vo.out.GetPostInfoResVo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetPostInfoResDto {

    private String memberUuid;
    private String postUuid;
    private String postTitle;

    @Builder
    public GetPostInfoResDto(String memberUuid, String postUuid, String postTitle) {
        this.memberUuid = memberUuid;
        this.postUuid = postUuid;
        this.postTitle = postTitle;
    }

    public static GetPostInfoResDto from(GetPostInfoResVo getPostInfoResVo) {
        return GetPostInfoResDto.builder()
                .memberUuid(getPostInfoResVo.getMemberUuid())
                .postUuid(getPostInfoResVo.getPostUuid())
                .postTitle(getPostInfoResVo.getPostTitle())
                .build();
    }
}
