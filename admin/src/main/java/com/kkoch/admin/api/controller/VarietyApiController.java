package com.kkoch.admin.api.controller;

import com.kkoch.admin.api.ApiResponse;
import com.kkoch.admin.api.controller.request.VarietyCreateRequest;
import com.kkoch.admin.api.service.variety.VarietyQueryService;
import com.kkoch.admin.api.service.variety.VarietyService;
import com.kkoch.admin.api.service.variety.response.VarietyCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin-service/varieties")
public class VarietyApiController {

    private final VarietyService varietyService;
    private final VarietyQueryService varietyQueryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<VarietyCreateResponse> createVariety(@Valid @RequestBody VarietyCreateRequest request) {
        int adminId = 1;

        VarietyCreateResponse response = varietyService.createVariety(adminId, request.toServiceRequest());

        return ApiResponse.created(response);
    }

    @GetMapping("/items")
    public ApiResponse<List<String>> searchItemNames(@RequestParam(defaultValue = "CUT_FLOWERS") String category) {
        List<String> response = varietyQueryService.searchItemNames(category);

        return ApiResponse.ok(response);
    }

    @GetMapping("/names")
    public ApiResponse<List<String>> searchVarietyNames(@RequestParam String item) {
        List<String> response = varietyQueryService.searchVarietyNames(item);

        return ApiResponse.ok(response);
    }
}
