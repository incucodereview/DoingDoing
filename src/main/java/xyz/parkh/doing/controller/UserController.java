package xyz.parkh.doing.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.parkh.doing.domain.entity.UserVo;
import xyz.parkh.doing.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping(value = "/api/users")
@RequiredArgsConstructor
@Api(tags = "사용자 정보")
public class UserController {

    private final UserService userService;

    // jwt
    // 사용자 정보 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserVo> userDetails(@PathVariable("userId") String userId, HttpServletRequest request) {
        String userIdInJwt = (String) request.getAttribute("userId");

        return userService.findUser(userIdInJwt, userId);
    }

    // jwt, email, name, company
    // 사용자 정보 수정
    @PutMapping
    public ResponseEntity userModify(@RequestBody UserVo userVo, HttpServletRequest request) {
        String userIdInJwt = (String) request.getAttribute("userId");

        return userService.modifyUser(userIdInJwt, userVo);
    }

    // jwt
    // 사용자 정보 삭제
    @DeleteMapping
    public ResponseEntity userRemove(HttpServletRequest request) {
        String userIdInJwt = (String) request.getAttribute("userId");

        return userService.removeUser(userIdInJwt);
    }
}
