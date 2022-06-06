package xyz.parkh.doing.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import xyz.parkh.doing.domain.entity.UserVo;
import xyz.parkh.doing.domain.model.RequestAuthKeyVo;

@Slf4j
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
        "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class AuthServiceTests {

    @Autowired
    AuthService authService;

//    @Test
//    public void 이메일_전송_오류() {
//        RequestAuthKeyVo requestAuthKeyVo = new RequestAuthKeyVo().builder().userId("park").email("fail").type(01).build();
//
//        authService.sendAuthKey(requestAuthKeyVo);
//    }

}
