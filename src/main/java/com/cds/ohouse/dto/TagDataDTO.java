package com.cds.ohouse.dto;

import com.cds.ohouse.domain.TradeState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagDataDTO {
    private String price;
    private int size;
    private TradeState state;

    public static TagDataDTO of(String price, int size, TradeState state) {
        return new TagDataDTO(
                price,
                size,
                state
        );
    }
}
