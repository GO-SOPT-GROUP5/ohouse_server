package com.cds.ohouse.dto.response;

import com.cds.ohouse.dto.response.CheckListCreateResponseVO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckListCreateResponseDTO {
  private Long id;
  private String title;
  private String address;
  private int dong;
  private int ho;
  private String image;
  private String description;

//  private String state;
//  private String price;
//  private int size;
  private int grade;
  private CheckListCreateResponseVO tag;




  public CheckListCreateResponseDTO(Long id, String title, String address, int dong, int ho, String image, int grade, CheckListCreateResponseVO checkListCreateResponseVO) {
    this.id = id;
    this.title = title;
    this.address = address;
    this.dong = dong;
    this.ho = ho;
    this.image = image;
    this.grade = grade;
    this.tag = checkListCreateResponseVO;
  }

  public static CheckListCreateResponseDTO of(Long id, String title, String address, int dong, int ho, String image, String description, int grade, CheckListCreateResponseVO tag){
  return new CheckListCreateResponseDTO(id, title, address, dong, ho, image, description, grade, tag);
}
//  public static CheckListCreateResponseDTO of(Long id,CheckListCreateRequestDTO checkListCreateRequestDTO, CheckListCreateResponseVO checkListCreateResponseVO) {
//    return new CheckListCreateResponseDTO(
//        id,
//        checkListCreateRequestDTO.getTitle(),
//        checkListCreateRequestDTO.getAddress(),
//        checkListCreateRequestDTO.getDong(),
//        checkListCreateRequestDTO.getHo(),
//        checkListCreateRequestDTO.getImage(),
//        checkListCreateRequestDTO.getGrade(),
//        checkListCreateResponseVO
//    );
//  }

}



