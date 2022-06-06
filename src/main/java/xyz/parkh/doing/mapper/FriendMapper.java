package xyz.parkh.doing.mapper;

import xyz.parkh.doing.domain.entity.FriendVo;

import java.util.List;

public interface FriendMapper {
    int insert(FriendVo FriendVo);

    FriendVo selectByRequesterId(String requesterId);

    FriendVo selectByAddresseeId(String addresseeId);

    List<FriendVo> selectAll();

    int update(FriendVo FriendVo);

    int delete(String requesterId, String addresseeId);
}
