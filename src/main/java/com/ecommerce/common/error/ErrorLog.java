package com.ecommerce.common.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorLog {
    private LocalDateTime errorTimeStamp = LocalDateTime.now();
    private final String error;
    private final HttpStatus statusCode;
}
