package com.unionclass.reviewservice.domain.review.factory;

import com.unionclass.reviewservice.domain.review.dto.in.ImageReqDto;
import com.unionclass.reviewservice.domain.review.entity.Image;
import com.unionclass.reviewservice.domain.review.enums.ImageType;
import com.unionclass.reviewservice.domain.review.util.ImageAltTextTemplateProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Imagefactory {

    private final ImageAltTextTemplateProvider imageAltTextTemplateProvider;

    public List<Image> createImages(List<ImageReqDto> images) {

        List<Image> imageList = new ArrayList<>();

        for (int i = 0; i < images.size() ; i++) {

            ImageType type = images.get(i).getType();
            String url = images.get(i).getImageUrl();
            String alt = imageAltTextTemplateProvider.getReviewImageAltTextTemplate(i + 1);

            imageList.add(
                    Image.builder()
                            .type(type)
                            .imageUrl(url)
                            .alt(alt)
                            .build()
            );
        }

        return imageList;
    }
}
