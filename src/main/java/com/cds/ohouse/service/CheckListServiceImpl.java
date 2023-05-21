package com.cds.ohouse.service;

import com.cds.ohouse.common.ErrorStatus;
import com.cds.ohouse.domain.*;
import com.cds.ohouse.dto.CategoryListDataDTO;
import com.cds.ohouse.dto.CategoryListDataVO;
import com.cds.ohouse.dto.TagDataDTO;
import com.cds.ohouse.dto.request.CheckListUpdateRequestDTO;
import com.cds.ohouse.dto.response.CheckListGetResponseDTO;
import com.cds.ohouse.dto.response.CheckListUpdateResponseDTO;
import com.cds.ohouse.dto.response.CheckListUpdateResponseVO;
import com.cds.ohouse.exception.CheckListException;
import com.cds.ohouse.repository.CategoryRepository;
import com.cds.ohouse.repository.CheckListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.val;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public HashMap<CategoryStatus, ArrayList<CategoryListDataVO>> arrangeCategories(List<Category> categoryList) {
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
