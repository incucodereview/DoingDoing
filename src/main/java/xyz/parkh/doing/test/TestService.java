package xyz.parkh.doing.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.parkh.doing.domain.entity.UserVo;
import xyz.parkh.doing.exception.ValueException;
import xyz.parkh.doing.mapper.UserMapper;

import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TestService {

    private final UserMapper userMapper;

    // 예외 만들기
    // https://veneas.tistory.com/entry/Java-%EC%BB%A4%EC%8A%A4%ED%85%80-%EC%98%88%EC%99%B8-%EB%A7%8C%EB%93%A4%EA%B8%B0Custom-Exception

    // Email 전송 후 딜레이 해결 방안
    // https://tecoble.techcourse.co.kr/post/2021-07-31-email-async-event/

    public void ExceptionHandlerTest(String data) {
        if ("".equals(data)) {
            throw new ValueException("TestService 에서 넘겨준 값");
        }
    }

    public void modifyUser(String userId, UserVo userVo) {
        // TODO userId 가 잘 못 되었으면 에러 뱉어야 되나?

        String email = userVo.getEmail();
        String company = userVo.getCompany();
        String name = userVo.getName();

        if ("".equals(email) && "".equals(company) && "".equals(name)) {
            throw new NullPointerException();
        }

        if (email == null && company == null && name == null) {
            throw new NullPointerException();
        }

        userVo.setUserId(userId);
        userMapper.update(userVo);

        // try catch 에 잡히니까 에러반환이 안되네... catch 에서 에러를 뱉어줘야되나?
        // 그럼 애초에 필요 없는거 아닌가 싶어서 일단 제외
//        try {
//            userVo.setUserId(userId);
//            userMapper.update(userVo);
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
    }

    public void customNull(String data) {
        if (Objects.isNull(data)) {
            throw new ValueException("testService 에서 넘긴 메시지");
        }
    }

//    public ResponseEntity customException(String data) {
//        if ("".equals(data)) {
//            throw new NullPointerException();
//        }
//        throw new CustomException("CustomException 발생");
//    }
}
