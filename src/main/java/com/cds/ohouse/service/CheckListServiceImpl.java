package com.cds.ohouse.service;

import com.cds.ohouse.common.ErrorStatus;
import com.cds.ohouse.domain.*;
import com.cds.ohouse.dto.CategoryListDataDTO;
import com.cds.ohouse.dto.CategoryListDataVO;
import com.cds.ohouse.dto.TagDataDTO;
import com.cds.ohouse.domain.Category;
import com.cds.ohouse.domain.Tag;
import com.cds.ohouse.domain.TradeState;
import com.cds.ohouse.dto.request.CheckListSortType;
import com.cds.ohouse.dto.request.CheckListUpdateRequestDTO;
import com.cds.ohouse.dto.response.CheckListGetResponseDTO;
import com.cds.ohouse.dto.response.CheckListUpdateResponseDTO;
import com.cds.ohouse.dto.response.CheckListUpdateResponseVO;
import com.cds.ohouse.dto.response.CheckListsGetResponseDTO;
import com.cds.ohouse.exception.CheckListException;
import com.cds.ohouse.repository.CategoryRepository;
import com.cds.ohouse.repository.CheckListRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CheckListServiceImpl implements CheckListService {
    private final CheckListRepository checkListRepository;
    private final CategoryRepository categoryRepository;

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
    public CheckListGetResponseDTO getCheckListById(Long id) {
        val checkList =  checkListRepository.findById(id)
            .orElseThrow(() -> new CheckListException(ErrorStatus.NOT_FOUND_CHECKLIST));

        val categoryList = checkList.getCategories();

        val tag = checkList.getTag();

        val TagData = TagDataDTO.of(tag.getPrice(), tag.getSize(), tag.getState());

        val arrangedCategoryList = arrangeCategories(categoryList);

        val indoorList = arrangedCategoryList.get(CategoryStatus.INDOOR);
        val kitchenList = arrangedCategoryList.get(CategoryStatus.KITCHEN);
        val livingRoomList = arrangedCategoryList.get(CategoryStatus.LIVINGROOM);
        val bathroomList = arrangedCategoryList.get(CategoryStatus.BATHROOM);

        val checkListData = CategoryListDataDTO.of(indoorList, kitchenList, livingRoomList, bathroomList);

        return CheckListGetResponseDTO.of(checkList, TagData, checkListData);
    }
  
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
  
   private HashMap<CategoryStatus, ArrayList<CategoryListDataVO>> arrangeCategories(List<Category> categoryList) {
        HashMap<CategoryStatus, ArrayList<CategoryListDataVO>> map = new HashMap<>();

        categoryList.stream().forEach(category -> {
            val mainCategory = category.getCategory();
            val subCategory = category.getSubCategory();
            val categoryId = category.getId();
            val categoryState = category.getState();

            val subCategoryData = CategoryListDataVO.of(categoryId, subCategory, categoryState);

            if(!map.containsKey(mainCategory)) {
                map.put(mainCategory, new ArrayList<>());
            }
            map.get(mainCategory).add(subCategoryData);
        });

        return map;
    }
}
