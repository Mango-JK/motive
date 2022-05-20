package com.fly.unicorn.motive.dto.response;

import lombok.*;

@ToString
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse implements Response {
    String timestamp;
    int errorCode;
    String errorMessage;
}
