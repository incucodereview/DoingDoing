package xyz.parkh.doing.mapper;

import xyz.parkh.doing.domain.entity.AuthVo;

public interface AuthMapper {
    int insert(AuthVo authVo);

    AuthVo selectByUserId(String userId);

    int update(AuthVo authVo);

    int delete(String authId);
}
