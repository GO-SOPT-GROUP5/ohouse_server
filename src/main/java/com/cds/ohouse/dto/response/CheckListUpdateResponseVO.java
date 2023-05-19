package com.cds.ohouse.dto.response;

import com.cds.ohouse.domain.Tag;
import com.cds.ohouse.domain.TradeState;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
@Getter
public class CheckListUpdateResponseVO {
    @NotBlank
    private TradeState state;
    @NotBlank
    private String price;
    @NotBlank
    private int size;

    public CheckListUpdateResponseVO(TradeState state, String price, int size) {
        this.state = state;
        this.price = price;
        this.size = size;
    }

    public static CheckListUpdateResponseVO of(Tag tag) {
        return new CheckListUpdateResponseVO(
                tag.getState(),
                tag.getPrice(),
                tag.getSize()
        );
    }
}
