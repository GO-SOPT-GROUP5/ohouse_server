package com.cds.ohouse.controller;

import com.cds.ohouse.common.ApiResponse;
import com.cds.ohouse.common.SuccessStatus;
import com.cds.ohouse.dto.request.CheckListUpdateRequestDTO;
import com.cds.ohouse.dto.response.CheckListUpdateResponseDTO;
import com.cds.ohouse.service.CheckListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/checklist")
public class CheckListController {
    private final CheckListService checkListService;

    @PatchMapping
    public ResponseEntity<ApiResponse> updateCheckList(@RequestBody CheckListUpdateRequestDTO request) {
        CheckListUpdateResponseDTO response = checkListService.updateCheckList(request);
        return ResponseEntity.ok(ApiResponse.success(SuccessStatus.UPDATE_CATEGORY_SUCCESS, response));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCheckList(@PathVariable Long id) {
        checkListService.deleteCheckList(id);
        return ResponseEntity.ok(ApiResponse.success(SuccessStatus.DELETE_CHECKLIST_SUCCESS));
    }
}
