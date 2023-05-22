package com.cds.ohouse.repository.checkList;

import com.cds.ohouse.domain.CheckList;
import com.cds.ohouse.domain.TradeState;
import com.cds.ohouse.dto.request.CheckListSortType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CheckListCustomRepository {
    List<CheckList> search(TradeState tradeState, CheckListSortType checkListSortType, Pageable pageable);
}
