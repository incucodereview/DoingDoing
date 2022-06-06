/**
 * 로그인, 회원가입, 사용자 확인 등
 * 사용자 인증 관련된 요청을 받는 Controller
 */

package xyz.parkh.doing.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import xyz.parkh.doing.domain.entity.UserVo;
import xyz.parkh.doing.service.email.EmailService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final TestService testService;
    private final EmailService emailService;

    @ResponseStatus(HttpStatus.BAD_GATEWAY) // 상태 코드만 이렇게 던지고 return 은 제대로 return 함, 근데 이 메서드는 항상 같은 상태 리턴..
    @PostMapping("/exception")
    public void postSignIn(@RequestBody String data) {
        testService.ExceptionHandlerTest(data);
    }

    // 사용자 수정 예외 처리 테스트
    @PostMapping("/user")
    public void postSignIn(@RequestBody UserVo userVo, HttpServletRequest request) {
        String userId = (String) request.getAttribute("userId");
        testService.modifyUser(userId, userVo);
    }

    // 사용자 수정 예외 처리 테스트
    @PostMapping("/custom-null")
    public void customNull(String data) {
        testService.customNull(data);
    }

//    @ResponseStatus(HttpStatus.BAD_GATEWAY) // 상태 코드만 이렇게 던지고 return 은 제대로 return 함, 근데 이 메서드는 항상 같은 상태 리턴..
//    @PostMapping("/null")
//    public ResponseEntity postSignIn(@RequestBody String data) {
//        return testService.nullPointException(data);
//    }
//
//    @ResponseStatus(HttpStatus.BAD_GATEWAY) // 상태 코드만 이렇게 던지고 return 은 제대로 return 함, 근데 이 메서드는 항상 같은 상태 리턴..
//    @PostMapping("/custom")
//    public ResponseEntity postSignIn(@RequestBody String data) {
//        return testService.nullPointException(data);
//    }


    @GetMapping("email")
    public void sendSimpleEmail(@RequestBody UserVo userVo) {
        String to = userVo.getEmail();
        String title = "DoingDoing Test Email";
        String content = "개발 테스트 중 입니다.";
        emailService.sendSimpleMessage(to, title, content);
    }

//    @Value("${test}")
//    String key;

//    @GetMapping("/key")
//    public void getKey() {
//        System.out.println("key = " + key);
//    }

//    public void setKey(String key) {
//        this.key = key;
//    }
}
