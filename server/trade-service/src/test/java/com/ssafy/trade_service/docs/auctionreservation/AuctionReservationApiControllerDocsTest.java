package com.ssafy.trade_service.docs.auctionreservation;

import com.ssafy.trade_service.api.controller.auctionreservation.AuctionReservationApiController;
import com.ssafy.trade_service.api.controller.auctionreservation.request.AuctionReservationCreateRequest;
import com.ssafy.trade_service.api.controller.auctionreservation.request.AuctionReservationModifyRequest;
import com.ssafy.trade_service.api.service.auctionreservation.AuctionReservationService;
import com.ssafy.trade_service.api.service.auctionreservation.response.AuctionReservationCreateResponse;
import com.ssafy.trade_service.api.service.auctionreservation.response.AuctionReservationModifyResponse;
import com.ssafy.trade_service.docs.RestDocsSupport;
import com.ssafy.trade_service.domain.auctionreservation.PlantGrade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionReservationApiControllerDocsTest extends RestDocsSupport {

    private final AuctionReservationService auctionReservationService = mock(AuctionReservationService.class);

    @Override
    protected Object initController() {
        return new AuctionReservationApiController(auctionReservationService);
    }

    @DisplayName("경매 예약 등록 API")
    @Test
    void createAuctionReservation() throws Exception {
        AuctionReservationCreateRequest request = AuctionReservationCreateRequest.builder()
            .varietyCode("10031204")
            .plantGrade("SUPER")
            .plantCount(10)
            .desiredPrice(3000)
            .build();

        AuctionReservationCreateResponse response = AuctionReservationCreateResponse.builder()
            .id(1L)
            .plantGrade(PlantGrade.SUPER)
            .plantCount(10)
            .desiredPrice(3000)
            .build();

        given(auctionReservationService.createAuctionReservation(anyInt(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations", 1)
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-auction-reservation",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                pathParameters(
                    parameterWithName("auctionScheduleId")
                        .description("경매 일정 ID")
                ),
                requestFields(
                    fieldWithPath("varietyCode").type(JsonFieldType.STRING)
                        .description("품종코드"),
                    fieldWithPath("plantGrade").type(JsonFieldType.STRING)
                        .description("화훼등급"),
                    fieldWithPath("plantCount").type(JsonFieldType.NUMBER)
                        .description("화훼단수"),
                    fieldWithPath("desiredPrice").type(JsonFieldType.NUMBER)
                        .description("희망가격")
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
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                        .description("경매 예약 ID"),
                    fieldWithPath("data.plantGrade").type(JsonFieldType.STRING)
                        .description("화훼등급"),
                    fieldWithPath("data.plantCount").type(JsonFieldType.NUMBER)
                        .description("화훼단수"),
                    fieldWithPath("data.desiredPrice").type(JsonFieldType.NUMBER)
                        .description("희망가격")
                )
            ));
    }

    @DisplayName("경매 예약 수정 API")
    @Test
    void modifyAuctionReservation() throws Exception {
        AuctionReservationModifyRequest request = AuctionReservationModifyRequest.builder()
            .plantGrade("ADVANCED")
            .plantCount(15)
            .desiredPrice(2500)
            .build();

        AuctionReservationModifyResponse response = AuctionReservationModifyResponse.builder()
            .id(1L)
            .plantGrade(PlantGrade.ADVANCED)
            .plantCount(15)
            .desiredPrice(2500)
            .build();

        given(auctionReservationService.modifyAuctionReservation(anyLong(), any()))
            .willReturn(response);

        mockMvc.perform(
                patch("/trade-service/auction-schedules/{auctionScheduleId}/auction-reservations/{auctionReservationId}", 1, 1)
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-auction-reservation",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                pathParameters(
                    parameterWithName("auctionScheduleId")
                        .description("경매 일정 ID"),
                    parameterWithName("auctionReservationId")
                        .description("경매 예약 ID")
                ),
                requestFields(
                    fieldWithPath("plantGrade").type(JsonFieldType.STRING)
                        .description("화훼등급"),
                    fieldWithPath("plantCount").type(JsonFieldType.NUMBER)
                        .description("화훼단수"),
                    fieldWithPath("desiredPrice").type(JsonFieldType.NUMBER)
                        .description("희망가격")
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
                    fieldWithPath("data.id").type(JsonFieldType.NUMBER)
                        .description("경매 예약 ID"),
                    fieldWithPath("data.plantGrade").type(JsonFieldType.STRING)
                        .description("화훼등급"),
                    fieldWithPath("data.plantCount").type(JsonFieldType.NUMBER)
                        .description("화훼단수"),
                    fieldWithPath("data.desiredPrice").type(JsonFieldType.NUMBER)
                        .description("희망가격")
                )
            ));
    }
}
