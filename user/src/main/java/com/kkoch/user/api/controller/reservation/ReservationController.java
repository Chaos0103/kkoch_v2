package com.kkoch.user.api.controller.reservation;

import com.kkoch.user.api.ApiResponse;
import com.kkoch.user.api.PageResponse;
import com.kkoch.user.api.controller.reservation.param.ReservationSearchParam;
import com.kkoch.user.api.controller.reservation.request.ReservationCreateRequest;
import com.kkoch.user.api.controller.reservation.response.ReservationCreateResponse;
import com.kkoch.user.api.controller.reservation.response.ReservationResponse;
import com.kkoch.user.api.service.reservation.ReservationQueryService;
import com.kkoch.user.api.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{memberKey}/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReservationQueryService reservationQueryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ReservationCreateResponse> createReservation(
        @PathVariable String memberKey,
        @Valid @RequestBody ReservationCreateRequest request
    ) {
        ReservationCreateResponse response = reservationService.createReservation(memberKey, request.toServiceRequest());

        return ApiResponse.created(response);
    }

    @GetMapping
    public ApiResponse<PageResponse<ReservationResponse>> searchReservations(
        @PathVariable String memberKey,
        @Valid @ModelAttribute ReservationSearchParam param
    ) {
        Pageable pageable = param.getPage();

        PageResponse<ReservationResponse> response = reservationQueryService.searchReservations(memberKey, pageable);

        return ApiResponse.ok(response);
    }
}


