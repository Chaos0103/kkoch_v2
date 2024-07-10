package com.kkoch.user.api.service.reservation;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.api.controller.reservation.response.ReservationCreateResponse;
import com.kkoch.user.api.service.reservation.request.ReservationCreateServiceRequest;
import com.kkoch.user.client.PlantServiceClient;
import com.kkoch.user.domain.reservation.PlantGrade;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.Point;
import com.kkoch.user.domain.member.repository.MemberRepository;
import com.kkoch.user.domain.reservation.Reservation;
import com.kkoch.user.domain.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

class ReservationServiceTest extends IntegrationTestSupport {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private PlantServiceClient plantServiceClient;

    @DisplayName("거래 예약 정보를 입력 받아 신규 거래 예약을 등록한다.")
    @Test
    void createReservation() {
        //given
        Member member = createMember();
        ReservationCreateServiceRequest request = ReservationCreateServiceRequest.builder()
            .plantType("")
            .plantName("")
            .plantCount(10)
            .desiredPrice(3000)
            .plantGrade("SUPER")
            .build();

        given(plantServiceClient.searchPlantIdBy(any()))
            .willReturn(1);

        //when
        ReservationCreateResponse response = reservationService.createReservation(member.getMemberKey(), request);

        //then
        assertThat(response).isNotNull()
            .hasFieldOrPropertyWithValue("plantCount", 10)
            .hasFieldOrPropertyWithValue("desiredPrice", 3000)
            .hasFieldOrPropertyWithValue("plantGrade", PlantGrade.SUPER);

        List<Reservation> reservations = reservationRepository.findAll();
        assertThat(reservations).hasSize(1);
    }

    @DisplayName("거래 예약 ID를 입력 받아 거래 예약을 삭제한다.")
    @Test
    void removeReservation() {
        //given
        Member member = createMember();
        Reservation reservation = createReservation(member);

        //when
        long reservationId = reservationService.removeReservation(reservation.getId());

        //then
        Optional<Reservation> findReservation = reservationRepository.findById(reservationId);
        assertThat(findReservation).isPresent()
            .get()
            .hasFieldOrPropertyWithValue("isDeleted", true);
    }

    private Member createMember() {
        Member member = Member.builder()
            .isDeleted(false)
            .memberKey(generateMemberKey())
            .email("ssafy@ssafy.com")
            .pwd(passwordEncoder.encode("ssafy1234!"))
            .name("김싸피")
            .tel("010-1234-1234")
            .businessNumber("123-12-12345")
            .point(Point.builder()
                .value(0)
                .build()
            ).build();
        return memberRepository.save(member);
    }

    private Reservation createReservation(Member member) {
        Reservation reservation = Reservation.builder()
            .isDeleted(false)
            .plantCount(10)
            .desiredPrice(3000)
            .plantGrade(PlantGrade.SUPER)
            .member(member)
            .plantId(1)
            .build();
        return reservationRepository.save(reservation);
    }
}