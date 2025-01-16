package com.zefu.ssoa.module.bpm.controller.admin.definition.vo.expression;

import com.zefu.ssoa.framework.common.enums.CommonStatusEnum;
import com.zefu.ssoa.framework.common.pojo.PageParam;
import com.zefu.ssoa.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.zefu.ssoa.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - BPM 流程表达式分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmProcessExpressionPageReqVO extends PageParam {

    @Schema(description = "表达式名字", example = "李四")
    private String name;

    @Schema(description = "表达式状态", example = "1")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}