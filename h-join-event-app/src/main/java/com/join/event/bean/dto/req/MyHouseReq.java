package com.join.event.bean.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * </p>
 *
 * @author ljh
 * @since 2025/3/17
 */
@Data
@ApiModel("用户房间请求实体")
public class MyHouseReq {

    @ApiModelProperty("用户id")
    private Long userId;

    @NotNull
    @ApiModelProperty("我创建的房间 0-否 1-是")
    private Boolean isMyRoom;
}
