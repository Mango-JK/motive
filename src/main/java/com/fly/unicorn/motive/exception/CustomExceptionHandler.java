package com.fly.unicorn.motive.exception;

import com.fly.unicorn.motive.dto.response.ResultCode;
import com.fly.unicorn.motive.service.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final CommonService commonService;

    /**
     * 400
     * MethodArgumentNotValidException : @Valid 주석이 달린 인수가 유효성 검사에 실패할 때
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        String errorMessage = Arrays.toString(new List[]{errors});
        log.error("at:exception ma:{} message:{}", ex.getClass().getName(), errorMessage);
        return commonService.getErrorResponse(HttpStatus.BAD_REQUEST.value(), ResultCode.MISSING_INFO.getCode(), errorMessage);
    }

    /**
     *  handleBindException : class 바인딩에 실패할 때
     */
    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        String errorMessage = Arrays.toString(new List[]{errors});
        log.error("at:exception ma:{} message:{}", ex.getClass().getName(), errorMessage);
        return commonService.getErrorResponse(HttpStatus.BAD_REQUEST.value(), ResultCode.BINDING_FAIL.getCode(), errorMessage);
    }

    /**
     * TypeMismatchException : 핸들러가 예상한 class로 변경할 수 없을 때
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String errorMessage = ex.getValue() + " 값의 요청 타입 " + ex.getPropertyName() + "이 다음 타입과 같아야 합니다. " + ex.getRequiredType();
        log.error("at:exception ma:{} message:{}", ex.getClass().getName(), errorMessage);
        return commonService.getErrorResponse(HttpStatus.BAD_REQUEST.value(), ResultCode.TYPE_MISMATCH.getCode(), errorMessage);
    }

    /**
     * MissingServletRequestPartException : 멀티 파트 요청의 일부를 찾을 수 없을 때
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String errorMessage = ex.getRequestPartName() + " 요청 정보가 누락되었습니다.";
        log.error("at:exception ma:{} message:{}", ex.getClass().getName(), errorMessage);
        return commonService.getErrorResponse(HttpStatus.BAD_REQUEST.value(), ResultCode.MULTIPART_ERROR.getCode(), errorMessage);
    }

    /**
     * MissingServletRequestParameterException  : 요청에서 일부 파라미터를 찾을 수 없을 때
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String errorMessage = ex.getParameterName() + "가 누락되었습니다.";
        log.error("at:exception ma:{} message:{}", ex.getClass().getName(), errorMessage);
        return commonService.getErrorResponse(HttpStatus.BAD_REQUEST.value(), ResultCode.MISSING_INFO.getCode(), errorMessage);
    }

    /**
     * MissingRequestHeaderException : 헤더 정보(AccessToken) 누락
     */
    @ExceptionHandler({MissingRequestHeaderException.class})
    public ResponseEntity<Object> missingRequestHeaderException(final MissingRequestHeaderException ex, final WebRequest request) {
        log.error("at:exception ma:{} message:{}", ex.getClass().getName(), ResultCode.MISSING_TOKEN.getMessage());
        return commonService.getErrorResponse(HttpStatus.BAD_REQUEST.value(), ResultCode.MISSING_TOKEN.getCode(), ResultCode.MISSING_TOKEN.getMessage());
    }

    /**
     * MethodArgumentTypeMismatchException : 메서드 파라미터가 예상 유형과 다를 때
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
        final String errorMessage = "메서드 파라미터가 잘못되었습니다.";
        log.error("at:exception ma:{} message:{}", ex.getClass().getName(), errorMessage);
        return commonService.getErrorResponse(HttpStatus.BAD_REQUEST.value(), ResultCode.TYPE_MISMATCH.getCode(), errorMessage);
    }

    /**
     * HttpMessageNotReadableException : 메서드 파라미터 형식이 다를 때
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMessage = "바인딩에 실패하였습니다. 요청 파라미터 형식을 확인해주세요.";
        log.error("at:exception ma:{} message:{}", ex.getClass().getName(), errorMessage);
        return commonService.getErrorResponse(HttpStatus.BAD_REQUEST.value(), ResultCode.TYPE_MISMATCH.getCode(), errorMessage);
    }

    /**
     * ConstraintViolationException : 제약 조건 위반의 결과
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
        final List<String> errors = new ArrayList<>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage());
        }
        String errorMessage = Arrays.toString(new List[]{errors});
        log.error("at:exception ma:{} message:{}", ex.getClass().getName(), errorMessage);
        return commonService.getErrorResponse(HttpStatus.BAD_REQUEST.value(), 50, errorMessage);
    }

    /**
     * 404 Error
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final String errorMessage = "요청 URL, Method가 올바르지 않습니다.";
        log.error("at:exception ma:{} message:{}", ex.getClass().getName(), errorMessage);
        return commonService.getErrorResponse(HttpStatus.NOT_FOUND.value(), ResultCode.NOT_FOUND.getCode(), errorMessage);
    }

    /**
     * 405 Error
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append(ResultCode.NOT_ALLOWED.getMessage());
        builder.append(ex.getMethod());
        String errorMessage = builder.toString();
        log.error("at:exception ma:{} message:{}", ex.getClass().getName(), errorMessage);
        return commonService.getErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), ResultCode.NOT_ALLOWED.getCode(), errorMessage);
    }

    /**
     * 415 Error
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        final StringBuilder builder = new StringBuilder();
        builder.append("지원하지 않는 미디어 타입입니다.");
        String errorMessage = builder.toString();
        log.error("at:exception ma:{} message:{}", ex.getClass().getName(), errorMessage);
        return commonService.getErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), ResultCode.UNSUPPORTED.getCode(), errorMessage);
    }

//    /**
//     * MethodArgumentTypeMismatchException : 토큰 인증에 실패하였을 때
//     */
//    @ExceptionHandler({MalformedJwtException.class, ExpiredJwtException.class})
//    public ResponseEntity<Object> malformedJwtException(final Exception ex, final WebRequest request) {
//        final String errorMessage = "JWT 토큰 인증에 실패하였습니다.";
//        log.error("at:exception ma:{} message:{}", ex.getClass().getName(), errorMessage);
//        return commonService.getErrorResponse(HttpStatus.UNAUTHORIZED.value(), ResultCode.UNAUTHORIZED.getCode(), errorMessage);
//    }

    /**
     * 500 Error
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
        String errorMessage = ex.getLocalizedMessage();
        log.error("at:exception ma:{} message:{}", ex.getClass().getName(), errorMessage);
        return commonService.getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResultCode.INTERNAL_SERVER_ERROR.getCode(), errorMessage);
    }
}
