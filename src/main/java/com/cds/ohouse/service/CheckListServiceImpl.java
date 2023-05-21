package com.cds.ohouse.service;

import com.cds.ohouse.common.ErrorStatus;
import com.cds.ohouse.domain.Category;
import com.cds.ohouse.domain.Tag;
import com.cds.ohouse.domain.TradeState;
import com.cds.ohouse.dto.request.CheckListSortType;
import com.cds.ohouse.dto.request.CheckListUpdateRequestDTO;
import com.cds.ohouse.dto.response.CheckListUpdateResponseDTO;
import com.cds.ohouse.dto.response.CheckListUpdateResponseVO;
import com.cds.ohouse.dto.response.CheckListsGetResponseDTO;
import com.cds.ohouse.exception.CheckListException;
import com.cds.ohouse.repository.checkList.CheckListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.val;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Override
    @Transactional
    public void deleteCheckList(Long id){
        val checkList = checkListRepository.findById(id)
            .orElseThrow(() -> new CheckListException(ErrorStatus.INVALID_CHECKLIST_EXCEPTION));
        checkListRepository.delete(checkList);
    }

    @Override
    @Transactional
    public List<CheckListsGetResponseDTO> getCheckLists(TradeState tradeState, CheckListSortType checkListSortType, Pageable pageable) {
        val checkLists = checkListRepository.search(tradeState, checkListSortType, pageable);

        return checkLists.stream().map(checkList -> {
            Map<Integer, Long> countsByStatus = checkList.getCategories().stream()
                    .collect(Collectors.groupingBy(Category::getState, Collectors.counting()));

            Long countGood = countsByStatus.getOrDefault(1, 0L);
            Long countAverage = countsByStatus.getOrDefault(2, 0L);
            Long countBad = countsByStatus.getOrDefault(3, 0L);

            return CheckListsGetResponseDTO.of(checkList, Math.toIntExact(countGood), Math.toIntExact(countAverage), Math.toIntExact(countBad));
        }).collect(Collectors.toList());
    }
}
