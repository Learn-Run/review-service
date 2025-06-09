package com.unionclass.reviewservice.client.post.application;

import com.unionclass.reviewservice.client.post.dto.GetPostInfoResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "community-service")
public interface CommunityServiceClient {

    @GetMapping("/api/v1/post/{postUuid}")
    GetPostInfoResDto getPostInfo(@PathVariable String postUuid);
}
