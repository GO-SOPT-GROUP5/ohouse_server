package com.cds.ohouse.service;

import com.cds.ohouse.common.ErrorStatus;
import com.cds.ohouse.domain.Category;
import com.cds.ohouse.domain.CheckList;
import com.cds.ohouse.dto.request.CategoryUpdateRequestDTO;
import com.cds.ohouse.exception.CheckListException;
import com.cds.ohouse.repository.CategoryRepository;
import com.cds.ohouse.repository.checkList.CheckListRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CheckListRepository checkListRepository;

    @Override
    @Transactional
    public void updateCategory(CategoryUpdateRequestDTO categoryUpdateRequestDTO) {
        val CategoryList = categoryUpdateRequestDTO.getCategoryList();

        CategoryList.stream().forEach(category -> {
            val categoryId = category.getId();
            val categoryInstance = getCategoryById(categoryId);
            val categoryState = category.getState();

            categoryInstance.updateStatus(categoryState);
        });

        val checkListId = categoryUpdateRequestDTO.getCheckListId();
        val checkList = getCheckListById(checkListId);

        updateCheckListState(checkList);
    }

    private Category getCategoryById(Long id) {
        return categoryRepository
                .findCategoryById(id)
                .orElseThrow(() -> new CheckListException(ErrorStatus.INVALID_CATEGORY_EXCEPTION));
    }
    private CheckList getCheckListById(Long id) {
        return checkListRepository
                .findCheckListById(id)
                .orElseThrow(() -> new CheckListException(ErrorStatus.INVALID_CHECKLIST_EXCEPTION));
    }

    private void updateCheckListState(CheckList checkList) {
        val checkListCategories = checkList.getCategories();

        HashMap<Integer, Integer> countMap = new HashMap<>();
        checkListCategories.stream().forEach(category -> {
            val categoryState = category.getState();

            if(!countMap.containsKey(categoryState)) {
                countMap.put(categoryState, 0);
            }

            countMap.put(categoryState, countMap.get(categoryState) + 1);
        });

        val goodCount = countMap.get(3);
        val averageCount = countMap.get(2);
        val badCount = countMap.get(1);

        val totalGoodCount = goodCount == null ? 0 : goodCount;
        val totalAverageCount = averageCount == null ? 0 : averageCount;
        val totalBadCount = badCount == null ? 0 : badCount;

        checkList.updateCategoryStatus(
                totalGoodCount,
                totalAverageCount,
                totalBadCount
        );
    }
}
