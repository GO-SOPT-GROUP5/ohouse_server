package com.cds.ohouse.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private final int code;
    private final String message;
    private T data;

    public static ApiResponse success(SuccessStatus success) {
        return new ApiResponse<>(success.getHttpStatusCode(), success.getMessage());
    }

    public static <T> ApiResponse<T> success(SuccessStatus success, T data) {
        return new ApiResponse<T>(success.getHttpStatusCode(), success.getMessage(), data);
    }

    public static ApiResponse error(ErrorStatus error) {
        return new ApiResponse<>(error.getHttpStatusCode(), error.getMessage());
    }

    public static ApiResponse error(ErrorStatus error, String message) {
        return new ApiResponse<>(error.getHttpStatusCode(), message);
    }
}