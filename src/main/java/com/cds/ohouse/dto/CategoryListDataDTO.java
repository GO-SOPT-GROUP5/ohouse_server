package com.cds.ohouse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListDataDTO {
  private ArrayList<CategoryListDataVO> indoor;
  private ArrayList<CategoryListDataVO> kitchen;
  private ArrayList<CategoryListDataVO> livingRoom;
  private ArrayList<CategoryListDataVO> bathroom;

  public static CategoryListDataDTO of(
          ArrayList<CategoryListDataVO> indoor,
          ArrayList<CategoryListDataVO> kitchen,
          ArrayList<CategoryListDataVO> livingRoom,
          ArrayList<CategoryListDataVO> bathroom
  ) {
    return new CategoryListDataDTO(
            indoor,
            kitchen,
            livingRoom,
            bathroom
    );
  }
}
