package com.cds.ohouse.dto.response;

import com.cds.ohouse.domain.CheckList;
import com.cds.ohouse.dto.CategoryListDataDTO;
import com.cds.ohouse.dto.TagDataDTO;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CheckListGetResponseDTO {
    @NotBlank
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String address;
    @NotBlank
    private int dong;
    @NotBlank
    private int ho;
    @NotBlank
    private String image;
    @NotBlank
    private int grade;
    @NotBlank
    private TagDataDTO tag;
    @NotBlank
    private CategoryListDataDTO checkListData;

    public CheckListGetResponseDTO(Long id, String title, String address, int dong, int ho, String image, int grade, TagDataDTO tag, CategoryListDataDTO categoryList) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.dong = dong;
        this.ho = ho;
        this.image = image;
        this.grade = grade;
        this.tag = tag;
        this.checkListData = categoryList;
    }

    public static CheckListGetResponseDTO of(CheckList checkList, TagDataDTO tag, CategoryListDataDTO categoryList) {
        return new CheckListGetResponseDTO(
            checkList.getId(),
            checkList.getTitle(),
            checkList.getAddress(),
            checkList.getDong(),
            checkList.getHo(),
            checkList.getImage(),
            checkList.getGrade(),
            tag,
            categoryList
        );
    }
}
