package com.join.event.bean.dto.req.house;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class CreateOrUpdateHouseReq {

    @ApiModelProperty(value = "id-修改时传")
    private Long id;

    @ApiModelProperty(value = "房间号")
    private Long number;

    @NotNull
    @ApiModelProperty(value = "房主")
    private Long houseOwner;

    @NotNull
    @ApiModelProperty(value = "房间名称")
    private String name;

    @ApiModelProperty(value = "房间简介")
    private String msg;

    @NotNull
    @ApiModelProperty(value = "活动开始时间")
    private LocalDateTime startTime;

    @NotNull
    @ApiModelProperty(value = "活动结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "预定活动人数")
    private Long personNum;
}
