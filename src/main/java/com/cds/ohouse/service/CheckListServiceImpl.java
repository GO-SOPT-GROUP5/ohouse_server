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
    @Override
    @Transactional
    public CheckListCreateResponseDTO createCheckList(CheckListCreateRequestDTO request){
        val tag = new Tag(
            request.getState(),
            request.getPrice(),
            request.getSize()
        );

        val checkList = CheckList.builder()
            .image(request.getImage())
            .address(request.getAddress())
            .title(request.getTitle())
            .dong(request.getDong())
            .ho(request.getHo())
            .description(request.getDescription())
            .grade(request.getGrade())
            .tag(tag)
            .build();

        val categoryList = request.getCheckListData();

        checkList.getTag().setCheckList(checkList);

        checkListRepository.save(checkList);

        val ckList = checkListRepository.findDistinctById(checkList.getId());



        System.out.println("test"+categoryList.getBathroom());

        val reqCategoryList = categoryList.getBathroom();

        reqCategoryList.stream().forEach(categoryListDataVO -> {
            System.out.println("test22" + categoryListDataVO.getState());
            System.out.println("test23" + categoryListDataVO.getSubCategoryStatus());
            System.out.println("333" + ckList);
            Category category = new Category(
                CategoryStatus.BATHROOM,
                categoryListDataVO.getSubCategoryStatus(),
                categoryListDataVO.getState(),
                ckList
            );
            categoryRepository.save(category);
        });

//
//            for (Category categoryRequest : request.getCategoryListDataDTO()) {
//                Category category = Category.builder()
//                    .category(categoryRequest.getCategory())
//                    .subCategory(categoryRequest.getSubCategory())
//                    .state(categoryRequest.getState())
//                    .checkList(checkList)
//                    .build();
//                createdCategories.add(category);
//            }
//            categoryRepository.saveAll(createdCategories);

//
//        HashMap<CategoryStatus, ArrayList<CategoryListDataVO>> arrangedCategories = arrangeCategories(createdCategories);
//
//        val indoorList = arrangedCategories.get(CategoryStatus.INDOOR);
//        val kitchenList = arrangedCategories.get(CategoryStatus.KITCHEN);
//        val livingRoomList = arrangedCategories.get(CategoryStatus.LIVINGROOM);
//        val bathroomList = arrangedCategories.get(CategoryStatus.BATHROOM);
//
//        val checkListData = CategoryListDataDTO.of(indoorList, kitchenList, livingRoomList, bathroomList);


        return CheckListCreateResponseDTO.of(
            checkList.getId(),
            checkList.getTitle(),
            checkList.getAddress(),
            checkList.getDong(),
            checkList.getHo(),
            checkList.getImage(),
            checkList.getDescription(),
            checkList.getGrade(),
            CheckListCreateResponseVO.of(tag)
            //categoryList
            //TODO: 카테고리 추가
        );
    }

}
