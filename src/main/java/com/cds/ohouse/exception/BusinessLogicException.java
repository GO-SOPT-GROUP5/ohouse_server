package com.cds.ohouse.exception;

import com.cds.ohouse.common.ErrorStatus;
import lombok.Getter;

@Getter
public class BusinessLogicException extends RuntimeException {
    private ErrorStatus errorStatus;

    public BusinessLogicException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }
}