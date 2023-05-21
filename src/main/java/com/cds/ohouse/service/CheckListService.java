package com.cds.ohouse.service;

import com.cds.ohouse.domain.CheckList;
import com.cds.ohouse.dto.request.CheckListUpdateRequestDTO;
import com.cds.ohouse.dto.response.CheckListGetResponseDTO;
import com.cds.ohouse.dto.response.CheckListUpdateResponseDTO;

public interface CheckListService {
    CheckListUpdateResponseDTO updateCheckList(CheckListUpdateRequestDTO checkListUpdateRequestDTO);
    CheckListGetResponseDTO getCheckListById(Long id);
}
