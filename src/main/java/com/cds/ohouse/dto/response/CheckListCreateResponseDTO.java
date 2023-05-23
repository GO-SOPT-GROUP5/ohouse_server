package com.cds.ohouse.dto.response;

import com.cds.ohouse.dto.CategoryListDataDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckListCreateResponseDTO {
  private Long id;
  private String title;
  private String address;
  private int dong;
  private int ho;
  private String image;
  private String description;
  private int grade;
  private CheckListCreateResponseVO tag;
  private CategoryListDataDTO checkListData;

  public static CheckListCreateResponseDTO of(
          Long id,
          String title,
          String address,
          int dong,
          int ho,
          String image,
          String description,
          int grade,
          CheckListCreateResponseVO tag,
          CategoryListDataDTO categoryList
  ){
      return new CheckListCreateResponseDTO(id, title, address, dong, ho, image, description, grade, tag, categoryList);
  }
}



