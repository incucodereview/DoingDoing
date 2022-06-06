package xyz.parkh.doing.utils;

import org.apache.commons.lang3.RandomStringUtils;
import xyz.parkh.doing.domain.entity.AuthKeyVo;
import xyz.parkh.doing.domain.entity.AuthVo;
import xyz.parkh.doing.domain.entity.ScheduleVo;
import xyz.parkh.doing.domain.entity.UserVo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Utils {

    // 테스트 UserVo 생성
    public static UserVo generateUser() {
        String userId = generatedStringWithInt(10);
        String name = generatedOnlyString(5);
        String company = generatedOnlyString(10);
        String email = generatedOnlyString(5) + "@" + generatedOnlyString(5) + "." + generatedOnlyString(3);

        UserVo user = new UserVo().builder()
                .userId(userId).name(name).company(company).email(email).build();

        return user;
    }

    // 테스트 AuthKeyVo 생성
    public static AuthKeyVo generateAuthKey(String userId) {
        // UserId 가 외래키로 되어 있어 사용자 만들어줘야 함
        // 너무 독립적이지 못한데.

        String authKey = generatedOnlyString(6);
        String email = generatedOnlyString(5) + "@" + generatedOnlyString(5) + "." + generatedOnlyString(3);
        LocalDateTime now = LocalDateTime.now();
        Integer type = 0;

        AuthKeyVo authKeyVo = new AuthKeyVo().builder()
                .userId(userId).authKey(authKey).crateTime(now).email(email).type(type).build();

        return authKeyVo;
    }

    // 테스트 AuthVo 생성
    public static AuthVo generateAuth(String userId) {
        String password = generatedStringWithInt(10);
        AuthVo authVo = new AuthVo().builder()
                .userId(userId).password(password).build();
        return authVo;
    }

    // 테스트 ScheduleVo 생성
    public static ScheduleVo generateSchedule(String userId) {
        String title = generatedOnlyString(5);
        String content = generatedOnlyString(10);
        Boolean isPublic = true;
        Boolean isComplete = true;
        // DB 저장시 milliseconds 단위 저장 안되고 반올림 되므로 withNano(0) 추가
        LocalDate endDate = LocalDate.now().plusDays(1);
        ScheduleVo schedule = new ScheduleVo().builder()
                .userId(userId).title(title).content(content).isPublic(isPublic).isComplete(isComplete).endDate(endDate).build();
        return schedule;
    }


    // 랜덤 String 생성 ( 문자열 )
    public static String generatedOnlyString(int length) {
        boolean useLetters = true;
        boolean useNumbers = false;

        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        return generatedString;
    }

    // 랜덤 String 생성 ( 문자열, 숫자 )
    public static String generatedStringWithInt(int length) {
        boolean useLetters = true;
        boolean useNumbers = true;

        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
        return generatedString;
    }

}
