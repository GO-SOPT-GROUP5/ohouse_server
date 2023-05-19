package com.cds.ohouse.exception;

import com.cds.ohouse.common.ErrorStatus;

public class CheckListException extends BusinessLogicException{
    public CheckListException(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
