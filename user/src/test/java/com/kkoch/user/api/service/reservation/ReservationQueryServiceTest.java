package com.kkoch.user.api.service.reservation;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.api.PageResponse;
import com.kkoch.user.api.service.reservation.response.ReservationResponse;
import com.kkoch.user.client.PlantServiceClient;
import com.kkoch.user.client.response.PlantResponse;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.Point;
import com.kkoch.user.domain.member.repository.MemberRepository;
import com.kkoch.user.domain.reservation.PlantGrade;
import com.kkoch.user.domain.reservation.Reservation;
import com.kkoch.user.domain.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

class ReservationQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private ReservationQueryService reservationQueryService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @MockBean
    private PlantServiceClient plantServiceClient;

    @DisplayName("회원 고유키를 입력 받아 거래 예약 목록을 조회한다.")
    @Test
    void searchReservations() {
        //given
        Member member = createMember();
        Reservation reservation1 = createReservation(member, 1, false);
        Reservation reservation2 = createReservation(member, 2, false);
        Reservation reservation3 = createReservation(member, 3, false);
        Reservation reservation4 = createReservation(member, 3, true);

        PageRequest pageRequest = PageRequest.of(0, 10);

        List<PlantResponse> plants = List.of(
            createPlant(1, "하젤"),
            createPlant(2, "하트앤소울"),
            createPlant(3, "핑크파티")
        );

        given(plantServiceClient.searchPlantsBy(any()))
            .willReturn(plants);

        //when
        PageResponse<ReservationResponse> response = reservationQueryService.searchReservations(member.getMemberKey(), pageRequest);

        //then
        assertThat(response)
            .hasFieldOrPropertyWithValue("currentPage", 1)
            .hasFieldOrPropertyWithValue("size", 10)
            .hasFieldOrPropertyWithValue("isFirst", true)
            .hasFieldOrPropertyWithValue("isLast", true);
        assertThat(response.getContent()).hasSize(3)
            .extracting("reservationId", "plantId", "plantType", "plantName")
            .containsExactly(
                tuple(reservation3.getId(), 3, "장미(스텐다드)", "핑크파티"),
                tuple(reservation2.getId(), 2, "장미(스텐다드)", "하트앤소울"),
                tuple(reservation1.getId(), 1, "장미(스텐다드)", "하젤")
            );
    }

    @DisplayName("회원 고유키를 입력 받아 거래 예약 목록을 조회한다.")
    @Test
    void searchReservationsIsEmpty() {
        //given
        Member member = createMember();
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        PageResponse<ReservationResponse> response = reservationQueryService.searchReservations(member.getMemberKey(), pageRequest);

        //then
        assertThat(response)
            .hasFieldOrPropertyWithValue("currentPage", 1)
            .hasFieldOrPropertyWithValue("size", 10)
            .hasFieldOrPropertyWithValue("isFirst", true)
            .hasFieldOrPropertyWithValue("isLast", true);
        assertThat(response.getContent()).isEmpty();
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
                .build())
            .build();
        return memberRepository.save(member);
    }

    private Reservation createReservation(Member member, int plantId, boolean isDeleted) {
        Reservation reservation = Reservation.builder()
            .isDeleted(isDeleted)
            .plantCount(10)
            .desiredPrice(4500)
            .plantGrade(PlantGrade.SUPER)
            .member(member)
            .plantId(plantId)
            .build();
        return reservationRepository.save(reservation);
    }

    private PlantResponse createPlant(int plantId, String plantName) {
        return PlantResponse.builder()
            .plantId(plantId)
            .plantType("장미(스텐다드)")
            .plantName(plantName)
            .build();
    }
}