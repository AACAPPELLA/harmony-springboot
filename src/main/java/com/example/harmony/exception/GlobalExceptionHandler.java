package com.example.harmony.exception;

import com.example.harmony.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.net.BindException;
import java.time.format.DateTimeParseException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({HandlerMethodValidationException.class})
    public ResponseDto<?> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
        log.error("Handler in HandlerMethodValidationException Error Message = " + e.getMessage());
        return ResponseDto.fail(new CommonException(ErrorCode.INVALID_ARGUMENT));
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class, MultipartException.class})
    public ResponseDto<?> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("Handler in HttpMediaTypeNotSupportedException Error Message = " + e.getMessage());
        return ResponseDto.fail(new CommonException(ErrorCode.UNSUPPORTED_MEDIA_TYPE));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseDto<?> handleNoHandlerFoundException(NoHandlerFoundException e) {
        log.error("Handler in NoHandlerFoundException Error Message = " + e.getMessage());
        return ResponseDto.fail(new CommonException(ErrorCode.NOT_END_POINT));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseDto<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("Handler in HttpMessageNotReadableException Error Message = " + e.getMessage());
        return ResponseDto.fail(new CommonException(ErrorCode.INVALID_ARGUMENT));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDto<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Handler in MethodArgumentNotValidException Error Message = " + e.getMessage());
        return ResponseDto.fail(e);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseDto<?> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("Handler in HttpRequestMethodNotSupportedException Error Message = " + e.getMessage());
        return ResponseDto.fail(new CommonException(ErrorCode.METHOD_NOT_ALLOWED));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseDto<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("Handler in MethodArgumentTypeMismatchException Error Message = " + e.getMessage());
        return ResponseDto.fail(new CommonException(ErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseDto<?> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("Handler in MissingServletRequestParameterException Error Message = " + e.getMessage());
        return ResponseDto.fail(new CommonException(ErrorCode.MISSING_REQUEST_PARAMETER));
    }

    @ExceptionHandler(CommonException.class)
    public ResponseDto<?> handleApiException(CommonException e) {
        log.error("Handler in CommonException Error Message = " + e.getMessage());
        return ResponseDto.fail(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseDto<?> handleException(Exception e) {
        log.error("Handler in Exception Error Message = " + e.getMessage(), e);
        return ResponseDto.fail(new CommonException(ErrorCode.SERVER_ERROR));
    }
}
