package com.cds.ohouse.service;

import com.cds.ohouse.common.ErrorStatus;
import com.cds.ohouse.domain.*;
import com.cds.ohouse.dto.CategoryListDataDTO;
import com.cds.ohouse.dto.CategoryListDataVO;
import com.cds.ohouse.dto.TagDataDTO;
import com.cds.ohouse.dto.request.CheckListCreateRequestDTO;
import com.cds.ohouse.dto.request.CheckListUpdateRequestDTO;
import com.cds.ohouse.dto.response.*;
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
        val livingRoomList = arrangedCategoryList.get(CategoryStatus.LIVING_ROOM);
        val bathroomList = arrangedCategoryList.get(CategoryStatus.BATH_ROOM);

        val checkListData = CategoryListDataDTO.of(indoorList, kitchenList, livingRoomList, bathroomList);

        return CheckListGetResponseDTO.of(checkList, TagData, checkListData);
    }

    @Override
    @Transactional
    public CheckListCreateResponseDTO createCheckList(CheckListCreateRequestDTO checkListCreateRequestDTO){
        List<Category> initalCategoryList = new ArrayList<>();

        val tag = Tag.builder()
                .state(checkListCreateRequestDTO.getState())
                .price(checkListCreateRequestDTO.getPrice())
                .size(checkListCreateRequestDTO.getSize())
                .build();

        val checkList = CheckList.builder()
                .image(checkListCreateRequestDTO.getImage())
                .address(checkListCreateRequestDTO.getAddress())
                .title(checkListCreateRequestDTO.getTitle())
                .dong(checkListCreateRequestDTO.getDong())
                .ho(checkListCreateRequestDTO.getHo())
                .description(checkListCreateRequestDTO.getDescription())
                .grade(checkListCreateRequestDTO.getGrade())
                .good(checkListCreateRequestDTO.getGood())
                .average(checkListCreateRequestDTO.getAverage())
                .bad(checkListCreateRequestDTO.getBad())
                .tag(tag)
                .categories(initalCategoryList)
                .build();

        checkList.getTag().setCheckList(checkList);

        val createdCheckList = checkListRepository.save(checkList);

        val categoryList = checkListCreateRequestDTO.getCheckListData();

        createCategories(CategoryStatus.LIVING_ROOM, categoryList, createdCheckList);
        createCategories(CategoryStatus.BATH_ROOM, categoryList, createdCheckList);
        createCategories(CategoryStatus.KITCHEN, categoryList, createdCheckList);
        createCategories(CategoryStatus.INDOOR, categoryList, createdCheckList);

        val arrangedCategoryList = arrangeCategories(createdCheckList.getCategories());

        val indoorList = arrangedCategoryList.get(CategoryStatus.INDOOR);
        val kitchenList = arrangedCategoryList.get(CategoryStatus.KITCHEN);
        val livingRoomList = arrangedCategoryList.get(CategoryStatus.LIVING_ROOM);
        val bathroomList = arrangedCategoryList.get(CategoryStatus.BATH_ROOM);

        val checkListData = CategoryListDataDTO.of(indoorList, kitchenList, livingRoomList, bathroomList);

        return CheckListCreateResponseDTO.of(
                checkList.getId(),
                checkList.getTitle(),
                checkList.getAddress(),
                checkList.getDong(),
                checkList.getHo(),
                checkList.getImage(),
                checkList.getDescription(),
                checkList.getGrade(),
                CheckListCreateResponseVO.of(tag),
                checkListData
        );
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

    private void createCategories(CategoryStatus categoryStatus, CategoryListDataDTO categoryList, CheckList createdCheckList) {
        ArrayList<CategoryListDataVO> categoryListByStatus = new ArrayList<>();
        switch (categoryStatus) {
            case BATH_ROOM:
                categoryListByStatus = categoryList.getBathroom();
                break;
            case LIVING_ROOM:
                categoryListByStatus = categoryList.getLivingRoom();
                break;
            case INDOOR:
                categoryListByStatus = categoryList.getIndoor();
                break;
            case KITCHEN:
                categoryListByStatus = categoryList.getKitchen();
                break;
            default:
                break;
        }

        categoryListByStatus.stream().forEach(categoryListDataVO -> {
            Category category = new Category(
                    categoryStatus,
                    categoryListDataVO.getSubCategoryStatus(),
                    categoryListDataVO.getState(),
                    createdCheckList
            );
            categoryRepository.save(category);
        });
    }

}
