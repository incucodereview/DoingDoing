package xyz.parkh.doing.mapper;

import xyz.parkh.doing.domain.entity.ScheduleVo;

import java.util.List;

public interface ScheduleMapper {
    int insert(ScheduleVo ScheduleVo);

    ScheduleVo selectByNo(Integer ScheduleNo);

    List<ScheduleVo> selectByUserId(String userId);

    int update(ScheduleVo ScheduleVo);

    int delete(Integer ScheduleNo);
}
