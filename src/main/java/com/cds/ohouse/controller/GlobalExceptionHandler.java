package com.cds.ohouse.controller;

import com.cds.ohouse.common.ApiResponse;
import com.cds.ohouse.common.ErrorStatus;
import com.cds.ohouse.exception.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return ApiResponse.error(ErrorStatus.VALIDATION_REQUEST_MISSING_EXCEPTION);
    }

    @ExceptionHandler(CheckListException.class)
    protected ApiResponse checkListException(final CheckListException e) {
        return ApiResponse.error(e.getErrorStatus());
    }
}
