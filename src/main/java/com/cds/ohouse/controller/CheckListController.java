package com.cds.ohouse.controller;

import com.cds.ohouse.common.ApiResponse;
import com.cds.ohouse.common.SuccessStatus;
import com.cds.ohouse.domain.CheckList;
import com.cds.ohouse.dto.request.CheckListCreateRequestDTO;
import com.cds.ohouse.dto.request.CheckListUpdateRequestDTO;
import com.cds.ohouse.dto.response.CheckListGetResponseDTO;
import com.cds.ohouse.dto.response.CheckListUpdateResponseDTO;
import com.cds.ohouse.domain.TradeState;
import com.cds.ohouse.dto.request.CheckListSortType;
import com.cds.ohouse.dto.request.CheckListUpdateRequestDTO;
import com.cds.ohouse.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/checklist")
public class CheckListController {
    private final CheckListService checkListService;

    @PatchMapping
    public ResponseEntity<ApiResponse> updateCheckList(@RequestBody CheckListUpdateRequestDTO request) {
        val response = checkListService.updateCheckList(request);
        return ResponseEntity.ok(ApiResponse.success(SuccessStatus.UPDATE_CATEGORY_SUCCESS, response));
    }
  
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCheckList(@PathVariable Long id) {
        val checkList = checkListService.getCheckListById(id);
        return ResponseEntity.ok(ApiResponse.success(SuccessStatus.GET_CHECKLIST_SUCCESS, checkList));
    }

    @PostMapping
    public ResponseEntity<ApiResponse>createCheckList(@RequestBody @Valid CheckListCreateRequestDTO request){
        val response = checkListService.createCheckList(request);
        return ResponseEntity.ok(ApiResponse.success(SuccessStatus.CREATE_CHECKLIST_SUCCESS, response));
    }
  
    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getCheckList(@RequestParam(required = false) TradeState flag, @RequestParam CheckListSortType order, Pageable pageable) {
        val response = checkListService.getCheckLists(flag, order, pageable);
        return ResponseEntity.ok(ApiResponse.success(SuccessStatus.UPDATE_CATEGORY_SUCCESS, response));
    }
      
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCheckList(@PathVariable Long id) {
        checkListService.deleteCheckList(id);
        return ResponseEntity.ok(ApiResponse.success(SuccessStatus.DELETE_CHECKLIST_SUCCESS));
    }
}
