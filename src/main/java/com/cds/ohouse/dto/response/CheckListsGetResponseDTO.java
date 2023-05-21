package com.cds.ohouse.dto.response;

import com.cds.ohouse.domain.CheckList;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class CheckListsGetResponseDTO {
    @NotBlank
    private Long id;
    @NotBlank
    private int grade;
    @NotBlank
    private String title;
    @NotBlank
    private String image;
    @NotBlank
    private int good;
    @NotBlank
    private int average;
    @NotBlank
    private int bad;

    public static CheckListsGetResponseDTO of(CheckList checkList, int good, int average, int bad) {
        return new CheckListsGetResponseDTO(
                checkList.getId(),
                checkList.getGrade(),
                checkList.getTitle(),
                checkList.getImage(),
                good,
                average,
                bad
        );
    }
}
