package xyz.parkh.doing.mapper;

import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.parkh.doing.domain.entity.ScheduleVo;
import xyz.parkh.doing.domain.entity.UserVo;
import xyz.parkh.doing.utils.Utils;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class ScheduleMapperTests {

    @Autowired
    ScheduleMapper scheduleMapper;

    @Autowired
    UserMapper userMapper;

    @Test
    @DisplayName("일정 등록, 조회, 수정, 삭제")
    public void scheduleCrud() {
        // 테스트 사용자 생성
        UserVo testUser = generateNewUser();
        String testUserId = testUser.getUserId();

        // 테스트 일정 생성
        ScheduleVo testSchedule = Utils.generateSchedule(testUserId);

        // 사용자 삽입
        int insertUserStatus = userMapper.insert(testUser);
        Assert.isTrue(insertUserStatus == 1);

        // TODO 적용이 안돼도 1 을 반환하네..
        // 일정 삽입
        int insertScheduleStatus = scheduleMapper.insert(testSchedule);
        Assert.isTrue(insertScheduleStatus == 1);

        // 일정 목록 조회
        List<ScheduleVo> selectAllSchedule = scheduleMapper.selectByUserId(testUserId);
        Assertions.assertNotNull(selectAllSchedule);

        // 일정 상세 조회
        ScheduleVo schedule = selectAllSchedule.get(0);
        Integer testScheduleNo = schedule.getNo();
        ScheduleVo selectSchedule = scheduleMapper.selectByNo(testScheduleNo);
        Assert.isTrue(schedule.equals(selectSchedule));

        // 일정 수정
        ScheduleVo newSchedule = Utils.generateSchedule(testUserId);
        newSchedule.setUserId(testUserId);
        newSchedule.setNo(selectSchedule.getNo());

        int updateStatus = scheduleMapper.update(newSchedule);
        Assert.isTrue(updateStatus == 1);

        // 일정 삭제
        int deleteStatus = scheduleMapper.delete(testScheduleNo);
        Assert.isTrue(deleteStatus == 1);
    }

    @Test
    @DisplayName("일정 부분 수정")
    public void updatePartOfSchedule() {
        // Given
        UserVo testUser = generateNewUser();
        String testUserId = testUser.getUserId();
        ScheduleVo testSchedule = Utils.generateSchedule(testUserId);

        int insertUserStatus = userMapper.insert(testUser);
        Assertions.assertEquals(insertUserStatus, 1);
        int insertScheduleStatus = scheduleMapper.insert(testSchedule);
        Assertions.assertEquals(insertScheduleStatus, 1);

        // 일정 목록 조회
        List<ScheduleVo> selectAllSchedule = scheduleMapper.selectByUserId(testUserId);
        Assertions.assertNotNull(selectAllSchedule);

        // 일정 상세 조회
        ScheduleVo schedule = selectAllSchedule.get(0);
        Integer testScheduleNo = schedule.getNo();
        ScheduleVo selectSchedule = scheduleMapper.selectByNo(testScheduleNo);
        Assertions.assertEquals(schedule, selectSchedule);

        // 부분 수정을 위한 일정 생성
        ScheduleVo updateSchedule = new ScheduleVo().builder()
                .userId(testUserId).no(testScheduleNo).content("newContent").build();

        // When
        int updateStatus = scheduleMapper.update(updateSchedule);
        Assertions.assertEquals(updateStatus, 1);

        // Then
        ScheduleVo selectScheduleAfterUpdate = scheduleMapper.selectByNo(testScheduleNo);

        // 기존
        Assertions.assertEquals(selectScheduleAfterUpdate.getTitle(), testSchedule.getTitle());
        Assertions.assertEquals(selectScheduleAfterUpdate.getIsPublic(), testSchedule.getIsPublic());
        Assertions.assertEquals(selectScheduleAfterUpdate.getEndDate(), testSchedule.getEndDate());

        // 변경
        Assertions.assertEquals(selectScheduleAfterUpdate.getContent(), updateSchedule.getContent());

    }

    // 새 사용자 생성
    private UserVo generateNewUser() {

        Utils utils = null;

        UserVo user;
        while (true) {
            user = utils.generateUser();
            if (userMapper.selectByUserId(user.getUserId()) == null) {
                break;
            }
        }
        return user;
    }
}
