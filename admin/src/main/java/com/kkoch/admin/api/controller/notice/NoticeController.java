package com.kkoch.admin.api.controller.notice;

import com.kkoch.admin.api.controller.admin.LoginAdmin;
import com.kkoch.admin.api.controller.notice.request.AddNoticeRequest;
import com.kkoch.admin.api.controller.notice.request.SetNoticeRequest;
import com.kkoch.admin.api.controller.notice.response.NoticeResponse;
import com.kkoch.admin.api.service.notice.NoticeQueryService;
import com.kkoch.admin.api.service.notice.NoticeService;
import com.kkoch.admin.api.service.notice.dto.NoticeCreateServiceRequest;
import com.kkoch.admin.api.service.notice.dto.NoticeModifyServiceRequest;
import com.kkoch.admin.login.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
@RequestMapping("/admin-service/intranet")

public class NoticeController {

    private final NoticeService noticeService;
    private final NoticeQueryService noticeQueryService;

    //공지사항 조회
    @GetMapping("/notice")
    public String adminBoardPage(@ModelAttribute("form") AddNoticeRequest request, Model model){
        List<NoticeResponse> responses = noticeQueryService.getAllNotices();
        model.addAttribute("notices",responses);
        return "notice";
    }

    @PostMapping("/notice/add")
    public String addNotice(AddNoticeRequest form, @Login LoginAdmin loginAdmin) {
        log.info("<공지 글 작성> Controller");
        NoticeCreateServiceRequest dto = form.toAddNoticeDto();
        return "redirect:/admin-service/intranet/notice";
    }

    @PostMapping("/{noticeId}/edit")
    public String setNotice(@PathVariable Long noticeId, SetNoticeRequest form) {
        log.info("<공지 글 수정> Controller");
        NoticeModifyServiceRequest dto = form.toSetNoticeDto();
        return "redirect:/notice";
    }

    @PostMapping("/{noticeId}/remove")
    public String removeNotice(@PathVariable Long noticeId) {
        log.info("<공지 글 삭제> Controller");
        return "redirect:/admin-service/intranet/notice";
    }
}
