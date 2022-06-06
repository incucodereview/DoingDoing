package xyz.parkh.doing.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
// REQUESTER_ID, ADDRESSEE_ID, STATUS
public class FriendVo {
    private String requesterId;
    private String addresseeId;
    private String status;
}
