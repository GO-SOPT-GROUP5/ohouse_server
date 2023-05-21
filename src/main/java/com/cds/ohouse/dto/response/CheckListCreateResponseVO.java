package com.cds.ohouse.dto.response;

import com.cds.ohouse.domain.Tag;
import com.cds.ohouse.domain.TradeState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckListCreateResponseVO {

  private TradeState state;

  private String price;

  private int size;

  public CheckListCreateResponseVO(TradeState state, String price, int size) {
    this.state = state;
    this.price = price;
    this.size = size;
  }

  public static CheckListCreateResponseVO of(Tag tag) {
    return new CheckListCreateResponseVO(
        tag.getState(),
        tag.getPrice(),
        tag.getSize()
    );
  }
}
