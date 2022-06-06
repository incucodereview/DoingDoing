package xyz.parkh.doing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.parkh.doing.domain.entity.ScheduleVo;
import xyz.parkh.doing.domain.model.ScheduleShortInfo;
import xyz.parkh.doing.domain.model.ScheduleStatusDto;
import xyz.parkh.doing.exception.DifferentAuthException;
import xyz.parkh.doing.exception.ValueException;
import xyz.parkh.doing.mapper.ScheduleMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleMapper scheduleMapper;

    // 접근 제한의 경우 부정을 먼저

    // 일정 상세 조회
    public ResponseEntity<ScheduleVo> findSchedule(final String userIdInJwt, final String userId, final Integer scheduleNo) {
        // 필수 인자가 없는 경우
        if (userIdInJwt == null || userId == null || scheduleNo == null) {
            throw new ValueException("필수 인자가 없습니다.");
        }

        // TODO 친구 기능 추가 시 권한 확인하는 코드 작성 필요, 현재 본인 정보만 조회 가능.
        // 본인이 아닐 경우
        if (!userId.equals(userIdInJwt)) {
            throw new DifferentAuthException("접근 할 수 있는 권한이 없습니다.");
        }

        ScheduleVo schedule = scheduleMapper.selectByNo(scheduleNo);

        // 일정이 없을 경우
        if (schedule == null) {
            throw new ValueException("조회 할 일정이 없습니다.");
        }

        return ResponseEntity.ok().body(schedule);
    }

    // 목록 조회
    // 1. 완료 O
    // 2. 완료 X, 기한 O ( 기한 지난 것, 안 지난 것 구분)
    // 3. 완료 X, 기한 X
    public ResponseEntity<List<ScheduleShortInfo>> findScheduleList(
            final String userIdInJwt, final String userId, final ScheduleStatusDto scheduleStatusDto) {
        Boolean requestIsComplete = scheduleStatusDto.getIsComplete();
        Boolean requestHasDeadLine = scheduleStatusDto.getHasDeadLine();

        // 필수 인자가 없는 경우
        if (userIdInJwt == null || userId == null || requestIsComplete == null) {
            throw new ValueException("필수 인자가 없습니다.");
        }

        // 완료 되지 않은 일정 조회 시 기한이 있는 것 없는 것 구분 필수
        if (!requestIsComplete && requestHasDeadLine == null) {
            throw new ValueException("필수 인자가 없습니다.");
        }

        // TODO 친구 기능 추가 시 권한 확인하는 코드 작성 필요, 현재 본인 정보만 조회 가능.

        // 본인이 아닐 경우
        if (!userId.equals(userIdInJwt)) {
            throw new DifferentAuthException("접근 할 수 있는 권한이 없습니다.");
        }

        List<ScheduleVo> scheduleList = scheduleMapper.selectByUserId(userId);

        ArrayList<ScheduleShortInfo> scheduleShortInfoList = new ArrayList<>();

        for (ScheduleVo schedule : scheduleList) {
            Integer no = schedule.getNo();
            String title = schedule.getTitle();
            LocalDate endDate = schedule.getEndDate();
            Boolean isComplete = schedule.getIsComplete();
            LocalDate now = LocalDate.now();
            Boolean overDeadLine = null;

            // TODO 친구 기능 추가 시 조회한 schedule 의 isPublic 을 기반으로 공개 여부 추가 할 것.

            // 완료된 일정 조회
            if (requestIsComplete) {
                if (!isComplete) continue; // 완료되지 않은 일정일 경우 처리하지 않음

                // 기한이 있는 일정의 경우 기한 추가.
                if (!Objects.isNull(endDate)) {
                    overDeadLine = now.isAfter(endDate);
                }

                ScheduleShortInfo scheduleShortInfo = new ScheduleShortInfo().builder().no(no)
                        .endDate(endDate).overDeadLine(overDeadLine).title(title).build();
                scheduleShortInfoList.add(scheduleShortInfo);
            } else if (requestHasDeadLine) {
                // 완료 되지 않고 기한이 있는 일정 조회
                if (isComplete || endDate == null) continue; // 완료 되었거나 기한이 없는 일정일 경우 처리하지 않음

                overDeadLine = now.isAfter(endDate);

                ScheduleShortInfo scheduleShortInfo = new ScheduleShortInfo().builder().no(no).title(title)
                        .overDeadLine(overDeadLine).endDate(endDate).build();
                scheduleShortInfoList.add(scheduleShortInfo);
            } else {
                // 완료 되지 않고 기한이 없는 일정 조회
                if (isComplete || endDate != null) continue; // 완료 되었거나 기한이 있는 일정일 경우 처리하지 않음

                ScheduleShortInfo scheduleShortInfo = new ScheduleShortInfo().builder().no(no).title(title).build();
                scheduleShortInfoList.add(scheduleShortInfo);
            }
        }
        return ResponseEntity.ok().body(scheduleShortInfoList);
    }

    // 일정 추가
    public ResponseEntity addSchedule(final String userIdInJwt, final String userId, final ScheduleVo scheduleVo) {
        String title = scheduleVo.getTitle();
        Boolean isPublic = scheduleVo.getIsPublic();

        // 필수 인자가 없는 경우
        if (userIdInJwt == null || userId == null || title == null) {
            throw new ValueException("필수 인자가 없습니다.");
        }

        // TODO 친구가 글 작성하는 기능 추가 시 권한 확인하는 코드 작성 필요
        if (!userIdInJwt.equals(userId)) {
            throw new DifferentAuthException("접근 할 수 있는 권한이 없습니다.");
        }

        scheduleVo.setUserId(userIdInJwt);
        scheduleMapper.insert(scheduleVo);

        return ResponseEntity.noContent().build();
    }

    // 일정 수정
    public ResponseEntity modifySchedule(final String userIdInJwt, final String userId, final Integer scheduleNo, final ScheduleVo scheduleVo) {

        // 필수 인자가 없는 경우
        if (userIdInJwt == null || userId == null || scheduleNo == null) {
            throw new ValueException("필수 인자가 없습니다.");
        }

        if (!userId.equals(userIdInJwt)) {
            throw new DifferentAuthException("접근 할 수 있는 권한이 없습니다.");
        }

        ScheduleVo existScheduleVo = scheduleMapper.selectByNo(scheduleNo);
        if (existScheduleVo == null) {
            throw new ValueException("수정 할 일정이 없습니다.");
        }

        scheduleVo.setNo(scheduleNo);
        scheduleVo.setUserId(userId);
        scheduleMapper.update(scheduleVo);

        return ResponseEntity.noContent().build();
    }

    // 일정 삭제
    public ResponseEntity removeSchedule(final String userIdInJwt, final String userId, final Integer scheduleNo) {
        // 필수 인자가 없는 경우
        if (userIdInJwt == null || userId == null || scheduleNo == null) {
            throw new ValueException("필수 인자가 없습니다.");
        }

        if (!userId.equals(userIdInJwt)) {
            throw new DifferentAuthException("접근 할 수 있는 권한이 없습니다.");
        }

        scheduleMapper.delete(scheduleNo);

        return ResponseEntity.noContent().build();
    }
}
