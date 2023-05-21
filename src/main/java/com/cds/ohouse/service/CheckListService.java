package com.cds.ohouse.service;

import com.cds.ohouse.domain.TradeState;
import com.cds.ohouse.dto.request.CheckListSortType;
import com.cds.ohouse.dto.request.CheckListUpdateRequestDTO;
import com.cds.ohouse.dto.response.CheckListUpdateResponseDTO;
import com.cds.ohouse.dto.response.CheckListsGetResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CheckListService {
    CheckListUpdateResponseDTO updateCheckList(CheckListUpdateRequestDTO checkListUpdateRequestDTO);

    void deleteCheckList(Long id);

    List<CheckListsGetResponseDTO> getCheckLists(TradeState tradeState, CheckListSortType checkListSortType, Pageable pageable);
}
