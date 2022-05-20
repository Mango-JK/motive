package com.fly.unicorn.motive.dto.response;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponse<T> implements Response {
    String timestamp;
    private int resultCode;
    private T data;
    private String message;
}
