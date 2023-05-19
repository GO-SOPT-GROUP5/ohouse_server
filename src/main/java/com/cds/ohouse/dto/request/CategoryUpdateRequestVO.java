package com.cds.ohouse.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CategoryUpdateRequestVO {
    @NotBlank
    private Long id;
    @NotBlank
    private int status;

    public CategoryUpdateRequestVO(Long id, int status) {
        this.id = id;
        this.status = status;
    }

    public static CategoryUpdateRequestVO of(Long id, int status) {
        return new CategoryUpdateRequestVO(
                id,
                status
        );
    }
}
