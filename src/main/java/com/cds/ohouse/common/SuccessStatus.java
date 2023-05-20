package com.cds.ohouse.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access =  AccessLevel.PRIVATE)
public enum SuccessStatus {

    /**
     * 200 OK
     */
    UPDATE_CATEGORY_SUCCESS(HttpStatus.OK, "카테고리 수정이 완료됐습니다."),

    /**
     * 201 CREATED
     */
    CREATE_CATEGORY_SUCCESS(HttpStatus.CREATED, "카테고리 생성이 완료됐습니다."),
    DELETE_CHECKLIST_SUCCESS(HttpStatus.OK, "체크리스트가 삭제되었습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
