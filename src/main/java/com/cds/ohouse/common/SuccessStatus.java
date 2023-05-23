package com.cds.ohouse.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access =  AccessLevel.PRIVATE)
public enum SuccessStatus {

    /**
     * category
     */
    UPDATE_CATEGORY_SUCCESS(HttpStatus.OK, "카테고리 수정이 완료됐습니다."),

    /**
     *checkList
     **/
    UPDATE_CHECKLIST_SUCCESS(HttpStatus.OK,"매물 정보 수정이 완료됐습니다."),
    DELETE_CHECKLIST_SUCCESS(HttpStatus.OK, "체크리스트가 삭제되었습니다."),
    CREATE_CHECKLIST_SUCCESS(HttpStatus.CREATED, "체크리스트가 생성되었습니다."),
    GET_CHECKLIST_SUCCESS(HttpStatus.OK, "체크리스트가 조회되었습니다."),
    GET_CHECKLISTS_SUCCESS(HttpStatus.OK,"매물 목록이 조회되었습니다.")
    ;


    private final HttpStatus httpStatus;
    private final String message;

    public int getHttpStatusCode() {
        return httpStatus.value();
    }
}
