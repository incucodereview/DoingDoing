package xyz.parkh.doing.mapper;

import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.parkh.doing.domain.entity.AuthVo;
import xyz.parkh.doing.domain.entity.UserVo;
import xyz.parkh.doing.utils.Utils;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class AuthMapperTests {

    @Autowired
    AuthMapper authMapper;

    @Autowired
    UserMapper userMapper;

    @Test
    @DisplayName("사용자 인증 등록, 조회, 수정, 삭제")
    public void userCrud() {
        // 테스트 사용자 생성
        UserVo testUser = generateNewUser();
        String testUserId = testUser.getUserId();

        AuthVo testAuth = Utils.generateAuth(testUserId);
        String testAuthPassword = testAuth.getPassword();

        // 사용자 인증 삽입
        // tb_user, tb_auth 참조 관계
        int insertUserStatus = userMapper.insert(testUser);
        Assert.isTrue(insertUserStatus == 1);
        int insertAuthStatus = authMapper.insert(testAuth);
        Assert.isTrue(insertAuthStatus == 1);

        // 사용자 인증 조회
        // TODO 암호화 적용 전
        AuthVo selectAuth = authMapper.selectByUserId(testUserId);
        Assert.isTrue(testAuthPassword.equals(selectAuth.getPassword()));

        // 사용자 인증 수정
        AuthVo newAuth = Utils.generateAuth(testUserId);
        newAuth.setPassword("newPassword");

        int authStatus = authMapper.update(newAuth);
        Assert.isTrue(authStatus == 1);

        // 사용자 인증 삭제
        int deleteStatus = authMapper.delete(testUserId);
        Assert.isTrue(deleteStatus == 1);
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
