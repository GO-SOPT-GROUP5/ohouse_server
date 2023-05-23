package com.cds.ohouse.service;

import com.cds.ohouse.common.ErrorStatus;
import com.cds.ohouse.domain.*;
import com.cds.ohouse.dto.CategoryListDataDTO;
import com.cds.ohouse.dto.CategoryListDataVO;
import com.cds.ohouse.dto.TagDataDTO;
import com.cds.ohouse.dto.request.CheckListCreateRequestDTO;
import com.cds.ohouse.dto.request.CheckListUpdateRequestDTO;
import com.cds.ohouse.dto.response.*;
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
import com.cds.ohouse.repository.checkList.CheckListRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.val;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
        val livingRoomList = arrangedCategoryList.get(CategoryStatus.LIVING_ROOM);
        val bathroomList = arrangedCategoryList.get(CategoryStatus.BATH_ROOM);

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
    public CheckListCreateResponseDTO createCheckList(CheckListCreateRequestDTO checkListCreateRequestDTO){
        val createdTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        val formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm 등록매물");
        val formattedDateTime = createdTime.format(formatter);

        List<Category> initalCategoryList = new ArrayList<>();

        val tag = Tag.builder()
                .state(checkListCreateRequestDTO.getState())
                .price(checkListCreateRequestDTO.getPrice())
                .size(checkListCreateRequestDTO.getSize())
                .build();

        val checkList = CheckList.builder()
                .image(checkListCreateRequestDTO.getImage())
                .address(checkListCreateRequestDTO.getAddress())
                .title(formattedDateTime)
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
  
    public List<CheckListsGetResponseDTO> getCheckLists(TradeState tradeState, CheckListSortType checkListSortType, int page, int size) {
        val checkLists = checkListRepository.search(tradeState, checkListSortType, page, size);

        return checkLists.stream().map(checkList -> {
            Map<Integer, Long> countsByStatus = checkList.getCategories().stream()
                    .collect(Collectors.groupingBy(Category::getState, Collectors.counting()));

            val countGood = countsByStatus.getOrDefault(3, 0L);
            val countAverage = countsByStatus.getOrDefault(2, 0L);
            val countBad = countsByStatus.getOrDefault(1, 0L);

            return CheckListsGetResponseDTO.of(checkList, Math.toIntExact(countGood), Math.toIntExact(countAverage), Math.toIntExact(countBad));
        }).collect(Collectors.toList());
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
