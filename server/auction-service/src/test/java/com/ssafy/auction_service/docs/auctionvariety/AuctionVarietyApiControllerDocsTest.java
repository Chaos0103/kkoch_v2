package com.ssafy.auction_service.docs.auctionvariety;

import com.ssafy.auction_service.api.controller.auctionvariety.AuctionVarietyApiController;
import com.ssafy.auction_service.api.controller.auctionvariety.request.AuctionVarietyCreateRequest;
import com.ssafy.auction_service.api.controller.auctionvariety.request.AuctionVarietyModifyRequest;
import com.ssafy.auction_service.api.service.auctionvariety.AuctionVarietyService;
import com.ssafy.auction_service.api.service.auctionvariety.response.AuctionVarietyCreateResponse;
import com.ssafy.auction_service.api.service.auctionvariety.response.AuctionVarietyModifyResponse;
import com.ssafy.auction_service.docs.RestDocsSupport;
import com.ssafy.auction_service.domain.auctionvariety.PlantGrade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuctionVarietyApiControllerDocsTest extends RestDocsSupport {

    private final AuctionVarietyService auctionVarietyService = mock(AuctionVarietyService.class);

    @Override
    protected Object initController() {
        return new AuctionVarietyApiController(auctionVarietyService);
    }

    @DisplayName("경매 품종 등록 API")
    @Test
    void createAuctionSchedule() throws Exception {
        AuctionVarietyCreateRequest request = AuctionVarietyCreateRequest.builder()
            .varietyCode("10000001")
            .auctionScheduleId(1)
            .plantGrade("SUPER")
            .plantCount(10)
            .auctionStartPrice(4500)
            .region("광주")
            .shipper("김출하")
            .build();

        AuctionVarietyCreateResponse response = AuctionVarietyCreateResponse.builder()
            .id(1L)
            .listingNumber("00001")
            .plantGrade(PlantGrade.SUPER)
            .plantCount(10)
            .auctionStartPrice(4500)
            .region("광주")
            .shipper("김출하")
            .createdDateTime(LocalDateTime.now())
            .build();

        given(auctionVarietyService.createAuctionVariety(anyString(), anyInt(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/auction-service/auction-varieties")
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-auction-variety",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                requestFields(
                    fieldWithPath("varietyCode").type(JsonFieldType.STRING)
                        .description("품종코드"),
                    fieldWithPath("auctionScheduleId").type(JsonFieldType.NUMBER)
                        .description("경매 일정 ID"),
                    fieldWithPath("plantGrade").type(JsonFieldType.STRING)
                        .description("화훼등급"),
                    fieldWithPath("plantCount").type(JsonFieldType.NUMBER)
                        .description("화훼단수"),
                    fieldWithPath("auctionStartPrice").type(JsonFieldType.NUMBER)
                        .description("경매 시작가"),
                    fieldWithPath("region").type(JsonFieldType.STRING)
                        .description("출하 지역"),
                    fieldWithPath("shipper").type(JsonFieldType.STRING)
                        .description("출하자")
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
                        .description("경매 품종 ID"),
                    fieldWithPath("data.listingNumber").type(JsonFieldType.STRING)
                        .description("경매번호"),
                    fieldWithPath("data.plantGrade").type(JsonFieldType.STRING)
                        .description("화훼등급"),
                    fieldWithPath("data.plantCount").type(JsonFieldType.NUMBER)
                        .description("화훼단수"),
                    fieldWithPath("data.auctionStartPrice").type(JsonFieldType.NUMBER)
                        .description("경매 시작가"),
                    fieldWithPath("data.region").type(JsonFieldType.STRING)
                        .description("출하 지역"),
                    fieldWithPath("data.shipper").type(JsonFieldType.STRING)
                        .description("출하자"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 품종 등록일시")
                )
            ));
    }

    @DisplayName("경매 품종 수정 API")
    @Test
    void modifyAuctionSchedule() throws Exception {
        AuctionVarietyModifyRequest request = AuctionVarietyModifyRequest.builder()
            .plantGrade("ADVANCED")
            .plantCount(15)
            .auctionStartPrice(4000)
            .build();

        AuctionVarietyModifyResponse response = AuctionVarietyModifyResponse.builder()
            .id(1L)
            .listingNumber("00001")
            .plantGrade(PlantGrade.ADVANCED)
            .plantCount(15)
            .auctionStartPrice(4000)
            .build();

        given(auctionVarietyService.modifyAuctionVariety(anyLong(), any()))
            .willReturn(response);

        mockMvc.perform(
                patch("/auction-service/auction-varieties/{auctionVarietyId}", 1)
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-auction-variety",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
                ),
                pathParameters(
                    parameterWithName("auctionVarietyId")
                        .description("경매 품종 ID")
                ),
                requestFields(
                    fieldWithPath("plantGrade").type(JsonFieldType.STRING)
                        .description("화훼등급"),
                    fieldWithPath("plantCount").type(JsonFieldType.NUMBER)
                        .description("화훼단수"),
                    fieldWithPath("auctionStartPrice").type(JsonFieldType.NUMBER)
                        .description("경매 시작가")
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
                        .description("경매 품종 ID"),
                    fieldWithPath("data.listingNumber").type(JsonFieldType.STRING)
                        .description("경매번호"),
                    fieldWithPath("data.plantGrade").type(JsonFieldType.STRING)
                        .description("화훼등급"),
                    fieldWithPath("data.plantCount").type(JsonFieldType.NUMBER)
                        .description("화훼단수"),
                    fieldWithPath("data.auctionStartPrice").type(JsonFieldType.NUMBER)
                        .description("경매 시작가")
                )
            ));
    }
}
