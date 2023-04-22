package com.sctk.cmc.common.exception;

import com.sctk.cmc.common.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CMCException.class)
    public ResponseEntity<Object> handleCMCException(CMCException e) {
        ResponseStatus status = e.getStatus();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse<>(status));
    }
}
