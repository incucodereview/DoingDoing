package xyz.parkh.doing.mapper;

import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.parkh.doing.domain.entity.AuthKeyVo;
import xyz.parkh.doing.domain.entity.UserVo;
import xyz.parkh.doing.utils.Utils;


@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class AuthKeyMapperTests {

    @Autowired
    AuthKeyMapper authKeyMapper;

    @Autowired
    UserMapper userMapper;

    @Test
    @DisplayName("인증키 생성, 조회")
    public void authKeyCrud() {
        // 테스트 인증키 생성
        UserVo testUser = generateNewUser();
        String testUserId = testUser.getUserId();
        String testEmail = testUser.getEmail();

        AuthKeyVo authKeyVo = Utils.generateAuthKey(testUserId);
        String authKey = authKeyVo.getAuthKey();
        log.info(testUser.toString());
        log.info(authKeyVo.toString());

        // userId 가 외래키이므로 User 생성
        int insertUserStatus = userMapper.insert(testUser);
        Assert.isTrue(insertUserStatus == 1);

        // 인증키 삽입
        int insertAuthKeyStatus = authKeyMapper.insert(authKeyVo);
        Assert.isTrue(insertAuthKeyStatus == 1);


        // TODO selectByUserId를 하면 최근 생성된 인증키를 조회 하도록 했는데 쿼리에 로직을 담은 건가?
        // TODO 지향해야 하나?
        // 최근 생성된 인증키 조회
        AuthKeyVo selectAuthKey = authKeyMapper.selectByUserIdWithEmail(authKeyVo);
        System.out.println("selectAuthKey = " + selectAuthKey);
        Assert.isTrue(authKey.equals(selectAuthKey.getAuthKey()));
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
