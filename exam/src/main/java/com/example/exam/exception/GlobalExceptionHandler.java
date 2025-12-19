package com.example.exam.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleValidation(IllegalArgumentException e) {
        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleServerError(RuntimeException e) {
        return ResponseEntity
                .status(500)
                .body(new ErrorResponse(e.getMessage()));
    }

    // 공통 에러 응답 객체
    @Getter
    @AllArgsConstructor
    public static class ErrorResponse {
        private String message;
    }
}
