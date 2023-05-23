package com.cds.ohouse.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class CheckListDataDTO {
  private ArrayList<CheckListDataVO> indoor = new ArrayList<>();
  private ArrayList<CheckListDataVO> kitchen = new ArrayList<>();
  private ArrayList<CheckListDataVO> livingRoom = new ArrayList<>();
  private ArrayList<CheckListDataVO> bathroom = new ArrayList<>();
}
