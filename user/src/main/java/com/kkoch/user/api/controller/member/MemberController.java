package com.kkoch.user.api.controller.member;

import com.kkoch.user.api.ApiResponse;
import com.kkoch.user.api.controller.member.request.MemberCreateRequest;
import com.kkoch.user.api.controller.member.request.MemberPwdModifyRequest;
import com.kkoch.user.api.controller.member.request.MemberRemoveRequest;
import com.kkoch.user.api.service.member.response.MemberResponse;
import com.kkoch.user.api.service.member.MemberQueryService;
import com.kkoch.user.api.service.member.MemberService;
import com.kkoch.user.domain.member.repository.response.MemberInfoResponse;
import com.kkoch.user.domain.member.repository.response.MemberResponseForAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberQueryService memberQueryService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MemberResponse> createMember(@Valid @RequestBody MemberCreateRequest request) {
        MemberResponse response = memberService.createMember(request.toJoinMemberDto());

        return ApiResponse.created(response);
    }

    @GetMapping("/{memberKey}")
    public ApiResponse<MemberInfoResponse> searchMember(@PathVariable String memberKey) {
        MemberInfoResponse response = memberQueryService.searchMember(memberKey);

        return ApiResponse.ok(response);
    }

    @PatchMapping("/{memberKey}/pwd")
    public ApiResponse<MemberResponse> modifyPwd(@PathVariable String memberKey, @Valid @RequestBody MemberPwdModifyRequest request) {
        MemberResponse response = memberService.modifyPwd(memberKey, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @PatchMapping("/{memberKey}/withdrawal")
    public ApiResponse<MemberResponse> removeMember(@PathVariable String memberKey, @Valid @RequestBody MemberRemoveRequest request) {
        MemberResponse response = memberService.removeMember(memberKey, request.toServiceRequest());

        return ApiResponse.ok(response);
    }

    @GetMapping("/members")
    public List<MemberResponseForAdmin> getUsers() {
        return memberQueryService.getUsers();
    }
}
