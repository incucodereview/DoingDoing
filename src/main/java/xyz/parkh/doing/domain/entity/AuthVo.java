package xyz.parkh.doing.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// USER_ID, PASSWORD
public class AuthVo {
    private String userId;
    private String password;
}
