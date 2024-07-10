package com.kkoch.user.api.service.reservation;

import com.kkoch.user.api.controller.reservation.response.ReservationCreateResponse;
import com.kkoch.user.api.service.reservation.request.ReservationCreateServiceRequest;
import com.kkoch.user.client.PlantServiceClient;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.repository.MemberRepository;
import com.kkoch.user.domain.reservation.Reservation;
import com.kkoch.user.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final PlantServiceClient plantServiceClient;

    public ReservationCreateResponse createReservation(String memberKey, ReservationCreateServiceRequest request) {
        Member member = findMemberBy(memberKey);

        Map<String, String> param = request.createPlantMap();
        Integer plantId = plantServiceClient.searchPlantIdBy(param);

        Reservation reservation = request.toEntity(member, plantId);
        Reservation savedReservation = reservationRepository.save(reservation);

        return ReservationCreateResponse.of(savedReservation);
    }

    public long removeReservation(long reservationId) {
        reservationRepository.findById(reservationId)
            .ifPresentOrElse(
                Reservation::remove,
                () -> {
                    throw new NoSuchElementException("등록되지 않은 거래 예약입니다.");
                }
            );

        return reservationId;
    }

    private Member findMemberBy(String memberKey) {
        return memberRepository.findByMemberKey(memberKey)
            .orElseThrow(() -> new NoSuchElementException("등록되지 않은 회원입니다."));
    }
}