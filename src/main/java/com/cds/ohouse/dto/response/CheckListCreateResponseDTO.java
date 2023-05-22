package com.cds.ohouse.dto.response;

import com.cds.ohouse.domain.CheckList;
import com.cds.ohouse.dto.CategoryListDataDTO;
import com.cds.ohouse.dto.TagDataDTO;
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
  //private CategoryListDataDTO checkListData;
  //private C
  public CheckListCreateResponseDTO(Long id, String title, String address, int dong, int ho, String image, int grade, CheckListCreateResponseVO checkListCreateResponseVO) {
    this.id = id;
    this.title = title;
    this.address = address;
    this.dong = dong;
    this.ho = ho;
    this.image = image;
    this.grade = grade;
    this.tag = checkListCreateResponseVO;
//    this.checkListData = categoryList;
  }

  public static CheckListCreateResponseDTO of(Long id, String title, String address, int dong, int ho, String image, String description, int grade, CheckListCreateResponseVO tag){
  return new CheckListCreateResponseDTO(id, title, address, dong, ho, image, description, grade, tag);
}
//  public static CheckListCreateResponseDTO of(CheckList checkList, CheckListCreateResponseVO tag, CategoryListDataDTO categoryList) {
//    return new CheckListCreateResponseDTO(
//        checkList.getId(),
//        checkList.getTitle(),
//        checkList.getAddress(),
//        checkList.getDong(),
//        checkList.getHo(),
//        checkList.getImage(),
//        checkList.getGrade(),
//        tag,
//        categoryList
//    );
//  }

}



