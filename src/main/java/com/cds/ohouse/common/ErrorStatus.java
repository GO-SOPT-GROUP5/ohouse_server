package com.cds.ohouse.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorStatus {

    /**
     * 400 BAD REQUEST
     */
    REQUEST_VALIDATION_EXCEPTION(HttpStatus.BAD_REQUEST, "잘못된 요청입니다"),
    VALIDATION_REQUEST_MISSING_EXCEPTION(HttpStatus.BAD_REQUEST, "요청값이 입력되지 않았습니다."),
    INVALID_CHECKLIST_EXCEPTION(HttpStatus.BAD_REQUEST, "체크리스트가 존재하지 않습니다."),
    INVALID_CATEGORY_EXCEPTION(HttpStatus.BAD_REQUEST, "카테고리가 존재하지 않습니다."),

    /**
     * 404 NOT FOUND
     */
    NOT_FOUND_CATEGORY_EXCEPTION(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다"),
    NOT_FOUND_CHECKLIST(HttpStatus.NOT_FOUND, "체크리스트를 찾을 수 없습니다."),

    /**
     * 409 CONFLICT
     */
    ALREADY_EXIST_CATEGORY_EXCEPTION(HttpStatus.CONFLICT, "이미 존재하는 카테고리입니다"),


    /**
     * 500 INTERNAL SERVER ERROR
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 서버 에러가 발생했습니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
