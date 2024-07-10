package com.kkoch.user.docs.member;

import com.kkoch.user.api.controller.member.MemberController;
import com.kkoch.user.api.controller.member.request.MemberCreateRequest;
import com.kkoch.user.api.controller.member.request.MemberPwdModifyRequest;
import com.kkoch.user.api.controller.member.request.MemberRemoveRequest;
import com.kkoch.user.api.service.member.response.MemberResponse;
import com.kkoch.user.api.service.member.MemberQueryService;
import com.kkoch.user.api.service.member.MemberService;
import com.kkoch.user.docs.RestDocsSupport;
import com.kkoch.user.domain.member.repository.response.MemberInfoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MemberControllerDocsTest extends RestDocsSupport {

    private final MemberService memberService = mock(MemberService.class);
    private final MemberQueryService memberQueryService = mock(MemberQueryService.class);

    @Override
    protected Object initController() {
        return new MemberController(memberService, memberQueryService);
    }

    @DisplayName("신규 회원 등록 API")
    @Test
    void createMember() throws Exception {
        MemberCreateRequest request = MemberCreateRequest.builder()
            .email("ssafy@ssafy.com")
            .loginPw("ssafy1234!")
            .name("김싸피")
            .tel("010-1234-1234")
            .businessNumber("123-12-12345")
            .build();

        MemberResponse response = MemberResponse.builder()
            .memberKey(generateMemberKey())
            .email("ssafy@ssafy.com")
            .name("김싸피")
            .build();

        given(memberService.createMember(any()))
            .willReturn(response);

        mockMvc.perform(
                post("/join", UUID.randomUUID().toString())
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(document("create-member",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("email").type(JsonFieldType.STRING)
                        .description("이메일"),
                    fieldWithPath("loginPw").type(JsonFieldType.STRING)
                        .description("계정 비밀번호"),
                    fieldWithPath("name").type(JsonFieldType.STRING)
                        .description("회원명"),
                    fieldWithPath("tel").type(JsonFieldType.STRING)
                        .description("연락처"),
                    fieldWithPath("businessNumber").type(JsonFieldType.STRING)
                        .description("사업자 번호")
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
                    fieldWithPath("data.memberKey").type(JsonFieldType.STRING)
                        .description("회원 고유키"),
                    fieldWithPath("data.email").type(JsonFieldType.STRING)
                        .description("회원 이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("회원명")
                )
            ));
    }

    @DisplayName("회원 정보 조회 API")
    @Test
    void searchMember() throws Exception {
        MemberInfoResponse response = MemberInfoResponse.builder()
            .email("ssafy@ssafy.com")
            .name("김싸피")
            .tel("010-1234-1234")
            .businessNumber("123-12-12345")
            .build();

        given(memberQueryService.searchMember(anyString()))
            .willReturn(response);

        mockMvc.perform(
                get("/{memberKey}", generateMemberKey())
                    .header(HttpHeaders.AUTHORIZATION, "token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-member",
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("접근 토큰")
                ),
                pathParameters(
                    parameterWithName("memberKey")
                        .description("회원 고유키")
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
                        .description("회원 이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("회원명"),
                    fieldWithPath("data.tel").type(JsonFieldType.STRING)
                        .description("회원 연락처"),
                    fieldWithPath("data.businessNumber").type(JsonFieldType.STRING)
                        .description("회원 사업자 번호")
                )
            ));
    }

    @DisplayName("회원 비밀번호 변경 API")
    @Test
    void modifyPwd() throws Exception {
        String memberKey = generateMemberKey();

        MemberPwdModifyRequest request = MemberPwdModifyRequest.builder()
            .currentPwd("ssafy1234@")
            .newPwd("ssafyc204!")
            .build();

        MemberResponse response = MemberResponse.builder()
            .memberKey(memberKey)
            .email("ssafy@ssafy.com")
            .name("김싸피")
            .build();

        given(memberService.modifyPwd(anyString(), any()))
            .willReturn(response);

        mockMvc.perform(
                patch("/{memberKey}/pwd", memberKey)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("modify-pwd",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("접근 토큰")
                ),
                pathParameters(
                    parameterWithName("memberKey")
                        .description("회원 고유키")
                ),
                requestFields(
                    fieldWithPath("currentPwd").type(JsonFieldType.STRING)
                        .description("현재 비밀번호"),
                    fieldWithPath("newPwd").type(JsonFieldType.STRING)
                        .description("새 비밀번호")
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
                        .description("사용자 이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("사용자 이름"),
                    fieldWithPath("data.memberKey").type(JsonFieldType.STRING)
                        .description("사용자 고유키")
                )
            ));
    }

    @DisplayName("회원 탈퇴 API")
    @Test
    void removeMember() throws Exception {
        String memberKey = generateMemberKey();

        MemberRemoveRequest request = MemberRemoveRequest.builder()
            .pwd("ssafy1234!")
            .build();

        MemberResponse response = MemberResponse.builder()
            .memberKey(memberKey)
            .email("ssafy@ssafy.com")
            .name("김싸피")
            .build();

        given(memberService.removeMember(anyString(), any()))
            .willReturn(response);

        mockMvc.perform(
                patch("/{memberKey}/withdrawal", memberKey)
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.AUTHORIZATION, "token")
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("remove-member",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestHeaders(
                    headerWithName(HttpHeaders.AUTHORIZATION)
                        .description("접근 토큰")
                ),
                pathParameters(
                    parameterWithName("memberKey")
                        .description("회원 고유키")
                ),
                requestFields(
                    fieldWithPath("pwd").type(JsonFieldType.STRING)
                        .description("비밀번호")
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
                        .description("사용자 이메일"),
                    fieldWithPath("data.name").type(JsonFieldType.STRING)
                        .description("사용자 이름"),
                    fieldWithPath("data.memberKey").type(JsonFieldType.STRING)
                        .description("사용자 고유키")
                )
            ));
    }
}
