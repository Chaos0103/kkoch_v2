package com.kkoch.admin.docs.auctionvariety;

import com.kkoch.admin.api.controller.auctionvariety.AuctionVarietyApiController;
import com.kkoch.admin.api.controller.auctionvariety.request.AuctionVarietyCreateRequest;
import com.kkoch.admin.api.service.auctionvariety.AuctionVarietyQueryService;
import com.kkoch.admin.api.service.auctionvariety.AuctionVarietyService;
import com.kkoch.admin.api.service.auctionvariety.response.AuctionVarietyCreateResponse;
import com.kkoch.admin.docs.RestDocsSupport;
import com.kkoch.admin.domain.Grade;
import com.kkoch.admin.domain.auctionvariety.repository.response.AuctionVarietyResponse;
import com.kkoch.admin.domain.variety.PlantCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuctionVarietyApiControllerDocsTest extends RestDocsSupport {

    private final AuctionVarietyService auctionVarietyService = mock(AuctionVarietyService.class);
    private final AuctionVarietyQueryService auctionVarietyQueryService = mock(AuctionVarietyQueryService.class);

    @Override
    protected Object initController() {
        return new AuctionVarietyApiController(auctionVarietyService, auctionVarietyQueryService);
    }

    @DisplayName("경매 품종 신규 등록 API")
    @Test
    void createAuctionVariety() throws Exception {
        AuctionVarietyCreateRequest request = AuctionVarietyCreateRequest.builder()
            .varietyCode("10000001")
            .auctionScheduleId(1)
            .grade("SUPER")
            .plantCount(10)
            .startPrice(4500)
            .region("광주")
            .shipper("김판매")
            .build();

        AuctionVarietyCreateResponse response = AuctionVarietyCreateResponse.builder()
            .auctionNumber("00001")
            .createdDateTime(LocalDateTime.now())
            .build();

        given(auctionVarietyService.createAuctionVariety(anyInt(), anyString(), anyInt(), any()))
            .willReturn(response);

        mockMvc.perform(
                post("/admin-service/auction-varieties")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-auction-variety",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("varietyCode").type(JsonFieldType.STRING)
                        .description("품종 코드"),
                    fieldWithPath("auctionScheduleId").type(JsonFieldType.NUMBER)
                        .description("경매 일정 ID"),
                    fieldWithPath("grade").type(JsonFieldType.STRING)
                        .description("등급"),
                    fieldWithPath("plantCount").type(JsonFieldType.NUMBER)
                        .description("단수"),
                    fieldWithPath("startPrice").type(JsonFieldType.NUMBER)
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
                    fieldWithPath("data.auctionNumber").type(JsonFieldType.STRING)
                        .description("경매 번호"),
                    fieldWithPath("data.createdDateTime").type(JsonFieldType.ARRAY)
                        .description("경매 품종 등록일시")
                )
            ));
    }

    @DisplayName("경매 품종 목록 조회 API")
    @Test
    void searchAuctionVarieties() throws Exception {
        AuctionVarietyResponse response = AuctionVarietyResponse.builder()
            .auctionVarietyId(1L)
            .auctionNumber("00001")
            .varietyCode("10000001")
            .plantCategory(PlantCategory.CUT_FLOWERS)
            .itemName("장미")
            .varietyName("하트앤소울")
            .plantCount(10)
            .startPrice(4500)
            .grade(Grade.SUPER)
            .region("광주")
            .shipper("김판매")
            .build();

        given(auctionVarietyQueryService.searchAuctionVarietiesBy(anyInt()))
            .willReturn(List.of(response));

        mockMvc.perform(
                get("/admin-service/auction-varieties")
                    .queryParam("auctionScheduleId", "1")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-auction-varieties",
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("code").type(JsonFieldType.NUMBER)
                        .description("코드"),
                    fieldWithPath("status").type(JsonFieldType.STRING)
                        .description("상태"),
                    fieldWithPath("message").type(JsonFieldType.STRING)
                        .description("메시지"),
                    fieldWithPath("data").type(JsonFieldType.ARRAY)
                        .description("응답 데이터"),
                    fieldWithPath("data[].auctionVarietyId").type(JsonFieldType.NUMBER)
                        .description("경매 품종 ID"),
                    fieldWithPath("data[].auctionNumber").type(JsonFieldType.STRING)
                        .description("경매 번호"),
                    fieldWithPath("data[].varietyCode").type(JsonFieldType.STRING)
                        .description("품종 코드"),
                    fieldWithPath("data[].plantCategory").type(JsonFieldType.STRING)
                        .description("화훼부류"),
                    fieldWithPath("data[].itemName").type(JsonFieldType.STRING)
                        .description("품목명"),
                    fieldWithPath("data[].varietyName").type(JsonFieldType.STRING)
                        .description("품종명"),
                    fieldWithPath("data[].plantCount").type(JsonFieldType.NUMBER)
                        .description("단수"),
                    fieldWithPath("data[].startPrice").type(JsonFieldType.NUMBER)
                        .description("경매 시작가"),
                    fieldWithPath("data[].grade").type(JsonFieldType.STRING)
                        .description("등급"),
                    fieldWithPath("data[].region").type(JsonFieldType.STRING)
                        .description("출하지역"),
                    fieldWithPath("data[].shipper").type(JsonFieldType.STRING)
                        .description("출하자")
                )
            ));
    }
}
