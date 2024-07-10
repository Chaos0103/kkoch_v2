package com.kkoch.user.domain.reservation.repository;

import com.kkoch.user.IntegrationTestSupport;
import com.kkoch.user.domain.member.Member;
import com.kkoch.user.domain.member.Point;
import com.kkoch.user.domain.member.repository.MemberRepository;
import com.kkoch.user.domain.reservation.PlantGrade;
import com.kkoch.user.domain.reservation.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.*;

class ReservationQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ReservationQueryRepository reservationQueryRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 고유키를 입력 받아 거래 예약 ID 목록을 조회한다.")
    @Test
    void findAllIdByMemberKey() {
        //given
        Member member = createMember();
        Reservation reservation1 = createReservation(member, 1, false);
        Reservation reservation2 = createReservation(member, 2, false);
        Reservation reservation3 = createReservation(member, 3, false);
        Reservation reservation4 = createReservation(member, 1, true);

        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        List<Long> reservationIds = reservationQueryRepository.findAllIdByMemberKey(member.getMemberKey(), pageRequest);

        //then
        assertThat(reservationIds).hasSize(3)
            .containsExactly(reservation3.getId(), reservation2.getId(), reservation1.getId());
    }

    @DisplayName("거래 예약 ID 목록을 입력 받아 거래 예약 목록을 조회한다.")
    @Test
    void findAllByIdIn() {
        //given
        Member member = createMember();
        Reservation reservation1 = createReservation(member, 1, false);
        Reservation reservation2 = createReservation(member, 2, false);
        Reservation reservation3 = createReservation(member, 3, false);

        List<Long> reservationIds = List.of(reservation3.getId(), reservation2.getId(), reservation1.getId());

        //when
        List<Reservation> reservations = reservationQueryRepository.findAllByIdIn(reservationIds);

        //then
        assertThat(reservations).hasSize(3)
            .extracting("id", "plantId", "isDeleted")
            .containsExactly(
                tuple(reservation3.getId(), 3, false),
                tuple(reservation2.getId(), 2, false),
                tuple(reservation1.getId(), 1, false)
            );
    }

    @DisplayName("회원 고유키를 입력 받아 거래 예약 총 갯수를 조회한다.")
    @Test
    void countByMemberKey() {
        //given
        Member member = createMember();
        Reservation reservation1 = createReservation(member, 1, false);
        Reservation reservation2 = createReservation(member, 2, false);
        Reservation reservation3 = createReservation(member, 3, false);
        Reservation reservation4 = createReservation(member, 3, true);

        //when
        int totalCount = reservationQueryRepository.countByMemberKey(member.getMemberKey());

        //then
        assertThat(totalCount).isEqualTo(3);
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
}