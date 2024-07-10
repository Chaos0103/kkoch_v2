package com.kkoch.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kkoch.user.api.controller.alarm.AlarmController;
import com.kkoch.user.api.controller.member.MemberController;
import com.kkoch.user.api.controller.pointlog.PointLogController;
import com.kkoch.user.api.controller.reservation.ReservationController;
import com.kkoch.user.api.service.alarm.AlamService;
import com.kkoch.user.api.service.alarm.AlarmQueryService;
import com.kkoch.user.api.service.member.MemberQueryService;
import com.kkoch.user.api.service.member.MemberService;
import com.kkoch.user.api.service.pointlog.PointLogQueryService;
import com.kkoch.user.api.service.pointlog.PointLogService;
import com.kkoch.user.api.service.reservation.ReservationQueryService;
import com.kkoch.user.api.service.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@WithMockUser
@WebMvcTest(controllers = {
    MemberController.class, PointLogController.class,
    ReservationController.class, AlarmController.class
})
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected MemberService memberService;

    @MockBean
    protected MemberQueryService memberQueryService;

    @MockBean
    protected PointLogService pointLogService;

    @MockBean
    protected PointLogQueryService pointLogQueryService;

    @MockBean
    protected ReservationService reservationService;

    @MockBean
    protected ReservationQueryService reservationQueryService;

    @MockBean
    protected AlamService alamService;

    @MockBean
    protected AlarmQueryService alarmQueryService;

    protected String generateMemberKey() {
        return UUID.randomUUID().toString();
    }
}
