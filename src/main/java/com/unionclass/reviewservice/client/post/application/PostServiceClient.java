package com.unionclass.reviewservice.client.post.application;

import com.unionclass.reviewservice.client.post.dto.GetPostInfoResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "post-service")
public interface PostServiceClient {

    @PostMapping("/api/v1/post/{postUuid}")
    GetPostInfoResDto getPostInfo(@PathVariable String postUuid);
}
