package com.cds.ohouse.service;

import com.cds.ohouse.domain.CheckList;
import com.cds.ohouse.dto.request.CheckListCreateRequestDTO;
import com.cds.ohouse.dto.request.CheckListUpdateRequestDTO;
import com.cds.ohouse.dto.response.CheckListCreateResponseDTO;
import com.cds.ohouse.dto.response.CheckListGetResponseDTO;
import com.cds.ohouse.dto.response.CheckListUpdateResponseDTO;

public interface CheckListService {
    CheckListUpdateResponseDTO updateCheckList(CheckListUpdateRequestDTO checkListUpdateRequestDTO);
    CheckListCreateResponseDTO createCheckList(CheckListCreateRequestDTO checkListCreateRequestDTO);
    CheckListGetResponseDTO getCheckListById(Long id);
}
