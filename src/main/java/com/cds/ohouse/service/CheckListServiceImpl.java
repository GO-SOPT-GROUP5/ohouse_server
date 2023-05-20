package com.cds.ohouse.service;

import com.cds.ohouse.common.ErrorStatus;
import com.cds.ohouse.domain.CheckList;
import com.cds.ohouse.domain.Tag;
import com.cds.ohouse.dto.request.CheckListUpdateRequestDTO;
import com.cds.ohouse.dto.response.CheckListUpdateResponseDTO;
import com.cds.ohouse.dto.response.CheckListUpdateResponseVO;
import com.cds.ohouse.exception.CheckListException;
import com.cds.ohouse.repository.CheckListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.val;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckListServiceImpl implements CheckListService {
    private final CheckListRepository checkListRepository;

    @Override
    @Transactional
    public CheckListUpdateResponseDTO updateCheckList(CheckListUpdateRequestDTO checkListUpdateRequestDTO) {
        val checkList = checkListRepository
                .findCheckListById(checkListUpdateRequestDTO.getId())
                .orElseThrow(() -> new CheckListException(ErrorStatus.INVALID_CHECKLIST_EXCEPTION));

        val tag = new Tag(checkListUpdateRequestDTO.getState(), checkListUpdateRequestDTO.getPrice(), checkListUpdateRequestDTO.getSize());
        checkList.updateCategory(checkListUpdateRequestDTO);
        checkList.getTag().updateTag(tag);

        return CheckListUpdateResponseDTO.of(checkListUpdateRequestDTO, CheckListUpdateResponseVO.of(tag));
    }
    public void deleteCheckList(Long id){
        CheckList checkList = checkListRepository.findById(id)
            .orElseThrow(() -> new CheckListException(ErrorStatus.INVALID_CHECKLIST_EXCEPTION));
        checkListRepository.delete(checkList);
    }
}
