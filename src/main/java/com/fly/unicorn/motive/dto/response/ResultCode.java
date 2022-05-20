package com.fly.unicorn.motive.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode {
    OK("20", "요청을 성공적으로 완료하였습니다."),
	WORDING_CREATED("21", "신규 글귀를 성공적으로 업로드하였습니다."),
    MISSING_INFO("40", "필수 입력 정보가 누락되었습니다."),
    UNSUPPORTED("41", "지원하지 않는 미디어 타입입니다."),
    TYPE_MISMATCH("42", "요청 타입이 잘못되었습니다."),
    NOT_FOUND("44","요청 데이터를 찾을 수 없습니다."),
    UNAUTHORIZED("45", "계정 인증에 실패하였습니다. 계정 정보를 확인해주세요."),
    NOT_ALLOWED("47", "지원하지 않는 HTTP 메소드 입니다."),
    BINDING_FAIL("48", "Class 바인딩에 실패하였습니다."),
    MULTIPART_ERROR("49", "Multipart 일부 요청 정보가 누락되었습니다."),
    MISSING_TOKEN("50", "JWT 토큰 정보가 누락되었습니다."),
    INTERNAL_SERVER_ERROR("51", "서버 에러로 요청에 실패하였습니다.");

    private String code;
    private String message;

    public int getCode() {
        return Integer.parseInt(code);
    }
}
