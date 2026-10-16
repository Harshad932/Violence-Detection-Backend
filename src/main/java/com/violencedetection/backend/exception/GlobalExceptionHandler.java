package com.violencedetection.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex)
    {

        ApiErrorResponse error=new ApiErrorResponse(HttpStatus.CONFLICT.value(),ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationError(MethodArgumentNotValidException ex)
    {
        String message=ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error->error.getField()+": "+error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ApiErrorResponse error=new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), message);

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleAuthenticationException(AuthenticationException ex)
    {

        ApiErrorResponse error=new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid username or password");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);

    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex)
    {

        ApiErrorResponse error=new ApiErrorResponse(HttpStatus
                .PAYLOAD_TOO_LARGE.value(), "Video File is too large . Please Upload a smaller video.");

        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body(error);

    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingPart(MissingServletRequestPartException ex)
    {
        ApiErrorResponse error=new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(),
                "Please  Upload a video using 'file' field");

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleUnreadableValue(HttpMessageNotReadableException ex)
    {
        ApiErrorResponse error=new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), "Invalid request body");

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(Exception ex)
    {
        ApiErrorResponse error=new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),"An unexpected error occurred");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }


}
