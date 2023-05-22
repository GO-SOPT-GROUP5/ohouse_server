package com.cds.ohouse.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class CategoryUpdateRequestVO {
    @NotBlank
    private Long id;
    @NotBlank
    private int status;
}
