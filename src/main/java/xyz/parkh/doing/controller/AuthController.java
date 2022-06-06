/**
 * 로그인, 회원가입, 사용자 확인 등
 * 사용자 인증 관련된 요청을 받는 Controller
 */

package xyz.parkh.doing.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import xyz.parkh.doing.domain.entity.AuthKeyVo;
import xyz.parkh.doing.domain.entity.AuthVo;
import xyz.parkh.doing.domain.entity.UserVo;
import xyz.parkh.doing.domain.model.CheckDto;
import xyz.parkh.doing.domain.model.JwtCheckDto;
import xyz.parkh.doing.domain.model.RequestAuthKeyVo;
import xyz.parkh.doing.domain.model.UserAuthDto;
import xyz.parkh.doing.service.AuthService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Api(tags = "인증")
public class AuthController {

    private final AuthService authService;

    // UserId, Password
    // 로그인
    @PostMapping("/sign-in")
    @ApiOperation(value = "로그인", notes = "로그인 한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "UserId", value = "사용자 아이디", required = true),
            @ApiImplicitParam(name = "password", value = "사용자 비밀번호", required = true)
    })
    public ResponseEntity signIn(@ApiIgnore @RequestBody AuthVo authVo) {
        return authService.signIn(authVo);
    }

    // userId, password, email, name, company
    // 회원 가입
    @PostMapping("/sign-up")
    public ResponseEntity signUp(@RequestBody UserAuthDto userAuthDto) {
        return authService.signUp(userAuthDto);
    }

    // userId, email, type
    // 인증 번호 전송
    @PostMapping("/send/auth-key")
    public ResponseEntity authKeySend(@RequestBody RequestAuthKeyVo requestAuthKeyVo) {
        return authService.sendAuthKey(requestAuthKeyVo);
    }

    // userId, authKey
    // 인증 번호 확인
    @PostMapping("/check/auth-key")
    public ResponseEntity<JwtCheckDto> authKeyCheck(@RequestBody AuthKeyVo authKeyVo) {
        return authService.checkAuthKey(authKeyVo);
    }

    //   Service 에서 ResopnseEntity 를 넘겨주는게 맞나? 라는 의문에
    //    이렇게 작성했는데 이러면 지금의 Error 처리 방식 사용 불가.
    //    위의 코드 유지
    //    public ResponseEntity<CheckDto> postCheckAuthKey(@RequestBody AuthKeyVo authKeyVo) {
    //        CheckDto checkDto = authService.checkAuthKey(authKeyVo);
    //        return ResponseEntity.ok().body(checkDto);
    //    }

    // userId
    // id 중복 확인
    @PostMapping("/check/user-id")
    public ResponseEntity<CheckDto> userIdCheck(@RequestBody AuthVo authVo) {
        return authService.checkUserId(authVo.getUserId());
    }

    // email
    // email 중복 확인
    @PostMapping("/check/email")
    public ResponseEntity<CheckDto> emailCheck(@RequestBody UserVo userVo) {
        return authService.checkEmail(userVo.getEmail());
    }

    // jwt, password
    // 사용자 확인을 위한 비밀번호 재확인
    @PostMapping("/check/password")
    public ResponseEntity<CheckDto> passwordCheck(@RequestBody AuthVo authVo, HttpServletRequest request) {
        String userIdInJwt = (String) request.getAttribute("userId");

        return authService.checkPassword(userIdInJwt, authVo);
    }

    // jwt, userId, password
    // 비밀번호 변경
    @PutMapping("/reset/password")
    public ResponseEntity passwordModify(@RequestBody AuthVo authVo, HttpServletRequest request) {
        String userIdInJwt = (String) request.getAttribute("userId");

        return authService.modifyPassword(userIdInJwt, authVo);
    }
}
