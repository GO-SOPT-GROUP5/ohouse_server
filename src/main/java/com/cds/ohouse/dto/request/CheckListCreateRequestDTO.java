package com.cds.ohouse.dto.request;

import com.cds.ohouse.domain.TradeState;
import com.cds.ohouse.dto.CategoryListDataDTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CheckListCreateRequestDTO {
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

    //protected CheckListCreateRequestDTO(){}
}
