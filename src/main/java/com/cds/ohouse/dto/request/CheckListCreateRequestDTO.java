package com.cds.ohouse.dto.request;

import com.cds.ohouse.domain.TradeState;
import com.cds.ohouse.dto.CategoryListDataDTO;
import com.cds.ohouse.dto.CheckListDataDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckListCreateRequestDTO {
  private String title;

  private String address;

  private int dong;

  private int ho;

  private String image;

  private String description;

  private TradeState state;

  private String price;
  
  private String address;
  
  private int dong;
  
  private int ho;
  
  private String image;
  
  private String description;
  
  private TradeState state;
  
  private String price;

  private int size;

  private int grade;

  private int good;

  private int average;

  private int bad;

  private CategoryListDataDTO checkListData;
}
