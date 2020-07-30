package io.spring.start.sample.hexagon.web.interfaces.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BaseResponse<T> {
    private final String status;
    private final String code;
    private final String message;
    private final T results;
}
