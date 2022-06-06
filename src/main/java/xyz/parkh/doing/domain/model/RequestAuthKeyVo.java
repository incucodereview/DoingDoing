package xyz.parkh.doing.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestAuthKeyVo {
    private String userId;
    private String email;
    private Integer type; // 00 : 비밀번호 찾기, 01 : 회원 가입
}
