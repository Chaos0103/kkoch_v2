package com.ssafy.user_service.docs.member;

import com.ssafy.user_service.api.controller.member.MemberQueryApiController;
import com.ssafy.user_service.api.service.member.MemberQueryService;
import com.ssafy.user_service.docs.RestDocsSupport;
import com.ssafy.user_service.domain.member.repository.response.MemberInfoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MemberQueryApiControllerDocsTest extends RestDocsSupport {

    private final MemberQueryService memberQueryService = mock(MemberQueryService.class);

    @Override
    protected Object initController() {
        return new MemberQueryApiController(memberQueryService);
    }

    @DisplayName("회원 정보 조회 API")
    @Test
    void searchMemberInfo() throws Exception {
        MemberInfoResponse response = MemberInfoResponse.builder()
            .email("ss***@ssafy.com")
            .name("김싸피")
            .tel("010****1234")
            .businessNumber("123*****45")
            .build();

        given(memberQueryService.searchMemberInfo(anyString()))
            .willReturn(response);

        mockMvc.perform(
                get("/members/info")
                    .header(HttpHeaders.AUTHORIZATION, "issued.access.token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-member-info",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("회원 토큰")
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
                    fieldWithPath("data.email").type(JsonFieldType.STRING)
                        .description("이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("회원명"),
                    fieldWithPath("data.tel").type(JsonFieldType.STRING)
                        .description("연락처"),
                    fieldWithPath("data.businessNumber").type(JsonFieldType.STRING)
                        .description("사업자 번호")
                )
            ));
    }
}
