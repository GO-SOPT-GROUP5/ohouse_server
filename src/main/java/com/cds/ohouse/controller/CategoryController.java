package com.cds.ohouse.controller;

import com.cds.ohouse.common.ApiResponse;
import com.cds.ohouse.common.SuccessStatus;
import com.cds.ohouse.dto.request.CategoryUpdateRequestDTO;
import com.cds.ohouse.dto.request.CheckListUpdateRequestDTO;
import com.cds.ohouse.dto.response.CheckListUpdateResponseDTO;
import com.cds.ohouse.service.CategoryService;
import com.cds.ohouse.service.CheckListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PatchMapping
    public ResponseEntity<ApiResponse> updateCheckList(@RequestBody CategoryUpdateRequestDTO request) {
        categoryService.updateCategory(request);
        //categoryService.updateCheckListStatus(request);
        return ResponseEntity.ok(ApiResponse.success(SuccessStatus.UPDATE_CATEGORY_SUCCESS));
    }
}
