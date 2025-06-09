package com.unionclass.reviewservice.domain.review.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.unionclass.reviewservice.common.exception.BaseException;
import com.unionclass.reviewservice.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Rating {

    RATING_ONE(1.0),
    RATING_ONE_POINT_FIVE(1.5),
    RATING_TWO(2.0),
    RATING_TWO_POINT_FIVE(2.5),
    RATING_THREE(3.0),
    RATING_THREE_POINT_FIVE(3.5),
    RATING_FOUR(4.0),
    RATING_FOUR_POINT_FIVE(4.5),
    RATING_FIVE(5.0)
    ;

    private final double value;

    @JsonValue
    public double getValue() { return value; }

    @JsonCreator
    public static Rating from(double input) {
        return Arrays.stream(values())
                .filter(r -> r.value == input)
                .findFirst()
                .orElseThrow(() -> new BaseException(ErrorCode.INVALID_RATING_VALUE));
    }
}
