package com.kkoch.user.api.service.reservation;

import com.kkoch.user.api.PageResponse;
import com.kkoch.user.api.controller.reservation.response.ReservationForAuctionResponse;
import com.kkoch.user.api.controller.reservation.response.ReservationResponse;
import com.kkoch.user.client.PlantServiceClient;
import com.kkoch.user.client.response.PlantResponse;
import com.kkoch.user.domain.reservation.Reservation;
import com.kkoch.user.domain.reservation.repository.ReservationQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservationQueryService {

    private final ReservationQueryRepository reservationQueryRepository;
    private final PlantServiceClient plantServiceClient;

    public PageResponse<ReservationResponse> searchReservations(String memberKey, Pageable pageable) {
        int totalCount = reservationQueryRepository.countByMemberKey(memberKey);

        List<Long> reservationIds = reservationQueryRepository.findAllIdByMemberKey(memberKey, pageable);
        if (CollectionUtils.isEmpty(reservationIds)) {
            return PageResponse.empty(pageable, totalCount);
        }

        List<Reservation> reservations = reservationQueryRepository.findAllByIdIn(reservationIds);

        List<Integer> plantIds = reservations.stream()
            .map(Reservation::getPlantId)
            .collect(Collectors.toList());

        Map<String, List<Integer>> param = Map.of("plantIds", plantIds);
        List<PlantResponse> plantNames = plantServiceClient.searchPlantsBy(param);

        Map<Integer, PlantResponse> plantMap = plantNames.stream()
            .collect(Collectors.toMap(PlantResponse::getPlantId, plantResponse -> plantResponse, (a, b) -> b));

        List<ReservationResponse> content = reservations.stream()
            .map(reservation -> ReservationResponse.of(reservation, plantMap.get(reservation.getPlantId())))
            .collect(Collectors.toList());

        return PageResponse.create(content, pageable, totalCount);
    }

    public ReservationForAuctionResponse getReservation(Long plantId) {
        return reservationQueryRepository.findByPlantId(plantId);
    }
}
