package com.join.event.bean.dto.res.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author ljh
 * @since 2025/3/17
 */
@Data
@ApiModel("用户活动列表")
public class UserHouseRes {

    @ApiModelProperty("活动id")
    private Long id;

    @ApiModelProperty(value = "房间号")
    private Long number;

    @ApiModelProperty(value = "房主")
    private Long houseOwner;

    @ApiModelProperty(value = "房间名称")
    private String name;

    @ApiModelProperty(value = "房间简介")
    private String msg;

    @ApiModelProperty(value = "房间类型")
    private Integer type;

    @ApiModelProperty(value = "活动状态 0-未开始 1-进行中 2-已结束")
    private Integer actStatus;

    @ApiModelProperty(value = "活动开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "活动结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "预定活动人数")
    private Long personNum;

}
