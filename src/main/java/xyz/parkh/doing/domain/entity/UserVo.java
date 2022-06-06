package xyz.parkh.doing.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// USER_ID, NAME, EMAIL, COMPANY
public class UserVo {
    private String userId;
    private String name;
    private String email;
    private String company;
}
