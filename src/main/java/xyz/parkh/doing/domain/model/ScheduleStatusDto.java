package xyz.parkh.doing.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleStatusDto {
    private Boolean isComplete; // 완료 됐는가?
    private Boolean hasDeadLine; // 마감 기한이 있는가?
}
