package com.cds.ohouse.dto.request;

import javax.validation.constraints.NotBlank;

import com.cds.ohouse.domain.TradeState;
import lombok.Getter;

@Getter
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

    public CheckListUpdateRequestDTO(Long id, String title, String address, int dong, int ho, String description, String image, int grade, TradeState state, String price, int size) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.dong = dong;
        this.ho = ho;
        this.description = description;
        this.image = image;
        this.grade = grade;
        this.state = state;
        this.price = price;
        this.size = size;
    }

    public static CheckListUpdateRequestDTO of(Long id, String title, String address, int dong, int ho, String description, String image, int grade, TradeState state, String price, int size) {
        return new CheckListUpdateRequestDTO(
                id,
                title,
                address,
                dong,
                ho,
                description,
                image,
                grade,
                state,
                price,
                size
        );
    }
}