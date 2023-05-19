package com.cds.ohouse.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
public class CategoryUpdateRequestDTO {
    @NotBlank
    private List<CategoryUpdateRequestVO> categoryList;
}
