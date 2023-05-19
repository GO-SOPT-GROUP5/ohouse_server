package com.cds.ohouse.dto.response;


import com.cds.ohouse.dto.request.CheckListUpdateRequestDTO;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class CheckListUpdateResponseDTO {
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
    private CheckListUpdateResponseVO tag;

    public CheckListUpdateResponseDTO(Long id, String title, String address, int dong, int ho, String image, int grade, CheckListUpdateResponseVO checkListUpdateResponseVO) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.dong = dong;
        this.ho = ho;
        this.image = image;
        this.grade = grade;
        this.tag = checkListUpdateResponseVO;
    }

    public static CheckListUpdateResponseDTO of(CheckListUpdateRequestDTO checkListUpdateRequestDTO, CheckListUpdateResponseVO checkListUpdateResponseVO) {
        return new CheckListUpdateResponseDTO(
                checkListUpdateRequestDTO.getId(),
                checkListUpdateRequestDTO.getTitle(),
                checkListUpdateRequestDTO.getAddress(),
                checkListUpdateRequestDTO.getDong(),
                checkListUpdateRequestDTO.getHo(),
                checkListUpdateRequestDTO.getImage(),
                checkListUpdateRequestDTO.getGrade(),
                checkListUpdateResponseVO
        );
    }
}