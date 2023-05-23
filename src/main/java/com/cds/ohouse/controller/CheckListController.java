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
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "매물 정보 수정")
    @PatchMapping
    public ResponseEntity<ApiResponse> updateCheckList(@RequestBody CheckListUpdateRequestDTO request) {
        val response = checkListService.updateCheckList(request);
        return ResponseEntity.ok(ApiResponse.success(SuccessStatus.UPDATE_CHECKLIST_SUCCESS, response));
    }

    @ApiOperation(value = "체크리스트 조회")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCheckList(@PathVariable Long id) {
        val checkList = checkListService.getCheckListById(id);
        return ResponseEntity.ok(ApiResponse.success(SuccessStatus.GET_CHECKLIST_SUCCESS, checkList));
    }

    @ApiOperation(value = "체크리스트 생성")
    @PostMapping
    public ResponseEntity<ApiResponse>createCheckList(@RequestBody @Valid CheckListCreateRequestDTO request){
        val response = checkListService.createCheckList(request);
        return ResponseEntity.ok(ApiResponse.success(SuccessStatus.CREATE_CHECKLIST_SUCCESS, response));
    }

    @ApiOperation(value = "매물 목록 조회")
    @GetMapping("/list")
    public ResponseEntity<ApiResponse> getCheckLists(@RequestParam(required = false) TradeState flag, @RequestParam CheckListSortType sort, @RequestParam int page, @RequestParam int size) {
        val response = checkListService.getCheckLists(flag, sort, page, size);
        return ResponseEntity.ok(ApiResponse.success(SuccessStatus.GET_CHECKLISTS_SUCCESS, response));
    }

    @ApiOperation(value = "체크리스트 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCheckList(@PathVariable Long id) {
        checkListService.deleteCheckList(id);
        return ResponseEntity.ok(ApiResponse.success(SuccessStatus.DELETE_CHECKLIST_SUCCESS));
    }
}
