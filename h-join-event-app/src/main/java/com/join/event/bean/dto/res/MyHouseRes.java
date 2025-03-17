package com.join.event.bean.dto.res;

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
public class MyHouseRes {

    @ApiModelProperty("用户活动id")
    private Long id;

    @ApiModelProperty(value = "房间号")
    private Long number;

    @ApiModelProperty(value = "房主")
    private Long houseOwner;

    @ApiModelProperty(value = "房间名称")
    private String name;

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

    @ApiModelProperty(value = "创建人")
    private Long createdUser;

    @ApiModelProperty(value = "修改人")
    private Long updatedUser;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updatedTime;

    @ApiModelProperty(value = "是否删除：(1：已删除，0：未删除)")
    private Boolean deleteStatus;
}
