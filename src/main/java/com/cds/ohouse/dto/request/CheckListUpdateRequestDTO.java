package com.cds.ohouse.dto.request;

import javax.validation.constraints.NotBlank;

import com.cds.ohouse.domain.TradeState;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CheckListUpdateRequestDTO {
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
    private String description;
    @NotBlank
    private String image;
    @NotBlank
    private int grade;
    @NotBlank
    private TradeState state;
    @NotBlank
    private String price;
    @NotBlank
    private int size;
}