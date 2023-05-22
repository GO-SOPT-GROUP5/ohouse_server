package com.cds.ohouse.dto;

import com.cds.ohouse.domain.SubCategoryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CategoryListDataVO {
  private Long id;
  private SubCategoryStatus subCategoryStatus;
  private int state;

  public static CategoryListDataVO of(Long id, SubCategoryStatus subCategoryStatus, int state) {
      return new CategoryListDataVO(
          id,
          subCategoryStatus,
          state
      );
  }
}
