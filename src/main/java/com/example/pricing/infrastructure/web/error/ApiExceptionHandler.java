package com.example.pricing.infrastructure.web.error;

import com.example.pricing.application.exception.PriceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@ControllerAdvice
public class ApiExceptionHandler {

    /** Modelo de error HTTP plano para la respuesta. */
    public record ApiError(int status, String error, String message, String path, OffsetDateTime timestamp) {
        public static ApiError of(HttpStatus s, String message, String path) {
            return new ApiError(s.value(), s.getReasonPhrase(), message, path, OffsetDateTime.now(ZoneOffset.UTC));
        }
    }

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<ApiError> handleAppNotFound(PriceNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiError.of(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<ApiError> handleBadRequest(Exception ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiError.of(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleUnprocessable(IllegalArgumentException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ApiError.of(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiError.of(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request.getRequestURI()));
    }
}
