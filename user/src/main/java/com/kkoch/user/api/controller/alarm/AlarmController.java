package com.kkoch.user.api.controller.alarm;

import com.kkoch.user.api.ApiResponse;
import com.kkoch.user.api.controller.alarm.response.AlarmResponse;
import com.kkoch.user.api.service.alarm.AlamService;
import com.kkoch.user.api.service.alarm.AlarmQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{memberKey}/alarms")
public class AlarmController {

    private final AlamService alamService;
    private final AlarmQueryService alarmQueryService;

    @GetMapping
    public ApiResponse<List<AlarmResponse>> getAlarms(@PathVariable String memberKey) {
        int openCount = alamService.openAllAlarm(memberKey);

        List<AlarmResponse> responses = alarmQueryService.searchAlarms(memberKey);

        return ApiResponse.ok(responses);
    }
}
