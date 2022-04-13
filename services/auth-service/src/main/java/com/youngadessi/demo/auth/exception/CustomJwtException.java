package com.youngadessi.demo.auth.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@RequiredArgsConstructor
public class CustomJwtException extends RuntimeException {
        private final String message;
        private final HttpStatus httpStatus;
}
