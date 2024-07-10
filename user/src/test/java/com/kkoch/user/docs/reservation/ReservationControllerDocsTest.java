package com.kkoch.user.docs.reservation;

import com.kkoch.user.api.PageResponse;
import com.kkoch.user.api.controller.reservation.ReservationController;
import com.kkoch.user.api.controller.reservation.request.ReservationCreateRequest;
import com.kkoch.user.api.controller.reservation.response.ReservationCreateResponse;
import com.kkoch.user.api.controller.reservation.response.ReservationResponse;
import com.kkoch.user.api.service.reservation.ReservationQueryService;
import com.kkoch.user.api.service.reservation.ReservationService;
import com.kkoch.user.docs.RestDocsSupport;
import com.kkoch.user.domain.reservation.PlantGrade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReservationControllerDocsTest extends RestDocsSupport {

    private final ReservationService reservationService = mock(ReservationService.class);
    private final ReservationQueryService reservationQueryService = mock(ReservationQueryService.class);

    @Override
    protected Object initController() {
        return new ReservationController(reservationService, reservationQueryService);
    }

    @DisplayName("거래 예약 등록 API")
    @Test
    void createReservation() throws Exception {
        ReservationCreateRequest request = ReservationCreateRequest.builder()
            .plantType("장미(스텐다드)")
            .plantName("하젤")
            .plantCount(10)
            .desiredPrice(4500)
            .plantGrade("SUPER")
            .build();

        ReservationCreateResponse response = ReservationCreateResponse.builder()
            .reservationId(1L)
            .plantCount(10)
            .desiredPrice(4500)
            .plantGrade(PlantGrade.SUPER)
            .createdDateTime(LocalDateTime.now())
            .build();

        given(reservationService.createReservation(anyString(), any()))
            .willReturn(response);

        mockMvc.perform(post("/{memberKey}/reservations", generateMemberKey())
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "token")
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-reservation",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("plantType").type(JsonFieldType.STRING)
                        .description("품목명"),
                    fieldWithPath("plantName").type(JsonFieldType.STRING)
                        .description("품종명"),
                    fieldWithPath("plantCount").type(JsonFieldType.NUMBER)
                        .description("희망 단수"),
                    fieldWithPath("desiredPrice").type(JsonFieldType.NUMBER)
                        .description("희망 가격"),
                    fieldWithPath("plantGrade").type(JsonFieldType.STRING)
                        .description("식물 등급(SUPER: 특급, ADVANCED: 상급, NORMAL: 보통)")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.reservationId").type(JsonFieldType.NUMBER)
                        .description("거래 예약 식별키"),
                    fieldWithPath("data.plantCount").type(JsonFieldType.NUMBER)
                        .description("희망 단수"),
                    fieldWithPath("data.desiredPrice").type(JsonFieldType.NUMBER)
                        .description("희망 가격"),
                    fieldWithPath("data.plantGrade").type(JsonFieldType.STRING)
                        .description("식물 등급"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("거래 예약 등록일시")
                )
            ));
    }

    @DisplayName("거래 예약 목록 조회 API")
    @Test
    void searchReservations() throws Exception {
        ReservationResponse response1 = createReservationResponse(1L, 2, "하트앤소울");
        ReservationResponse response2 = createReservationResponse(2L, 1, "하젤");
        ReservationResponse response3 = createReservationResponse(3L, 3, "핑크파티");
        List<ReservationResponse> content = List.of(response3, response2, response1);

        PageRequest pageRequest = PageRequest.of(0, 10);
        PageResponse<ReservationResponse> response = PageResponse.of(new PageImpl<>(content, pageRequest, 3));

        given(reservationQueryService.searchReservations(anyString(), any()))
            .willReturn(response);

        mockMvc.perform(
                get("/{memberKey}/reservations", generateMemberKey())
                    .param("page", "1")
                    .header("Authorization", "token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-reservations",
                preprocessResponse(prettyPrint()),
                requestParameters(
                    parameterWithName("page")
                        .optional()
                        .description("페이지 번호(기본값: 1)")
                ),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.OBJECT)
                        .description("응답 데이터"),
                    fieldWithPath("data.content").type(JsonFieldType.ARRAY)
                        .description("거래 예약 목록"),
                    fieldWithPath("data.content[].reservationId").type(JsonFieldType.NUMBER)
                        .description("거래 예약 식별키"),
                    fieldWithPath("data.content[].plantId").type(JsonFieldType.NUMBER)
                        .description("거래 예약 식물 식별키"),
                    fieldWithPath("data.content[].plantType").type(JsonFieldType.STRING)
                        .description("거래 예약 식물 품목명"),
                    fieldWithPath("data.content[].plantName").type(JsonFieldType.STRING)
                        .description("거래 예약 식물 품종명"),
                    fieldWithPath("data.content[].plantCount").type(JsonFieldType.NUMBER)
                        .description("거래 예약 식물 단수"),
                    fieldWithPath("data.content[].desiredPrice").type(JsonFieldType.NUMBER)
                        .description("거래 희망 가격"),
                    fieldWithPath("data.content[].plantGrade").type(JsonFieldType.STRING)
                        .description("거래 예약 식물 등급"),
                    fieldWithPath("data.content[].reservedDateTime").type(JsonFieldType.ARRAY)
                        .description("거래 예약 등록일시"),
                    fieldWithPath("data.currentPage").type(JsonFieldType.NUMBER)
                        .description("현재 페이지"),
                    fieldWithPath("data.size").type(JsonFieldType.NUMBER)
                        .description("조회된 데이터 갯수"),
                    fieldWithPath("data.isFirst").type(JsonFieldType.BOOLEAN)
                        .description("첫 페이지 여부"),
                    fieldWithPath("data.isLast").type(JsonFieldType.BOOLEAN)
                        .description("마지막 페이지 여부")
                )
            ));
    }

    private ReservationResponse createReservationResponse(long reservationId, int plantId, String plantName) {
        return ReservationResponse.builder()
            .reservationId(reservationId)
            .plantId(plantId)
            .plantType("장미(스텐다드)")
            .plantName(plantName)
            .plantCount(10)
            .desiredPrice(4500)
            .plantGrade(PlantGrade.SUPER)
            .reservedDateTime(LocalDateTime.now())
            .build();
    }
}
