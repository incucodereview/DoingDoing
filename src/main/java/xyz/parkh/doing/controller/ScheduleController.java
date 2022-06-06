package xyz.parkh.doing.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.parkh.doing.domain.entity.ScheduleVo;
import xyz.parkh.doing.domain.model.ScheduleShortInfo;
import xyz.parkh.doing.domain.model.ScheduleStatusDto;
import xyz.parkh.doing.service.ScheduleService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(("/api/schedules"))
@RequiredArgsConstructor
@Api(tags = "일정" )
public class ScheduleController {

    private final ScheduleService scheduleService;

    // jwt, isComplete, isTimeOut
    // 일정 목록 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<ScheduleShortInfo>> scheduleList(@PathVariable("userId") String userId,
                                                                @ModelAttribute ScheduleStatusDto ScheduleStatusDto,
                                                                HttpServletRequest request) {
        String userIdInJwt = (String) request.getAttribute("userId");

        return scheduleService.findScheduleList(userIdInJwt, userId, ScheduleStatusDto);
    }

    // jwt
    // 일정 정보 조회
    @GetMapping("/{userId}/{scheduleNo}")
    public ResponseEntity<ScheduleVo> scheduleDetails(@PathVariable("userId") String userId,
                                                      @PathVariable("scheduleNo") Integer scheduleNo,
                                                      HttpServletRequest request) {
        String userIdInJwt = (String) request.getAttribute("userId");

        return scheduleService.findSchedule(userIdInJwt, userId, scheduleNo);
    }

    // jwt, title, content, isPublic, endTime
    // 일정 정보 등록
    @PostMapping("/{userId}")
    public ResponseEntity scheduleAdd(@PathVariable("userId") String userId,
                                      @RequestBody ScheduleVo scheduleVo,
                                      HttpServletRequest request) {
        String userIdInJwt = (String) request.getAttribute("userId");

        return scheduleService.addSchedule(userIdInJwt, userId, scheduleVo);
    }

    // jwt, title, content, isPublic, endTime
    // 일정 정보 수정
    @PutMapping("/{userId}/{scheduleNo}")
    public ResponseEntity ScheduleModify(@PathVariable("userId") String userId,
                                         @PathVariable("scheduleNo") Integer scheduleNo,
                                         @RequestBody ScheduleVo scheduleVo,
                                         HttpServletRequest request) {
        String userIdInJwt = (String) request.getAttribute("userId");

        return scheduleService.modifySchedule(userIdInJwt, userId, scheduleNo, scheduleVo);
    }

    // 일정 정보 삭제
    @DeleteMapping("/{userId}/{scheduleNo}")
    public ResponseEntity scheduleRemove(@PathVariable("userId") String userId,
                                         @PathVariable("scheduleNo") Integer scheduleNo,
                                         HttpServletRequest request) {
        String userIdInJwt = (String) request.getAttribute("userId");

        return scheduleService.removeSchedule(userIdInJwt, userId, scheduleNo);
    }
}
