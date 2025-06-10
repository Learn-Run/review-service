package com.unionclass.reviewservice.client.post.application;

import com.unionclass.reviewservice.client.post.vo.out.GetPostInfoResVo;
import com.unionclass.reviewservice.common.response.BaseResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "post-service")
public interface PostServiceClient {

    @GetMapping("/api/v1/post/{postUuid}")
    BaseResponseEntity<GetPostInfoResVo> getPostInfo(@PathVariable String postUuid);
}
