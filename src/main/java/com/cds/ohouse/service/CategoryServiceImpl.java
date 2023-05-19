package com.cds.ohouse.service;

import com.cds.ohouse.common.ErrorStatus;
import com.cds.ohouse.domain.Category;
import com.cds.ohouse.dto.request.CategoryUpdateRequestDTO;
import com.cds.ohouse.exception.CheckListException;
import com.cds.ohouse.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public void updateCategory(CategoryUpdateRequestDTO categoryUpdateRequestDTO) {
        categoryUpdateRequestDTO.getCategoryList().stream().forEach(category -> {
            val categoryId = category.getId();
            val categoryInstance = getCategoryById(categoryId);

            categoryInstance.updateStatus(category.getStatus());
        });
    }

    private Category getCategoryById(Long id) {
        return categoryRepository
                .findCategoryById(id)
                .orElseThrow(() -> new CheckListException(ErrorStatus.INVALID_CHECKLIST_EXCEPTION));
    }

}
