package com.join.event.bean.dto.req.user;

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
 * @since 2025/3/14
 */
@Data
@ApiModel("用户登录请求实体")
public class UserLoginReq {

    @ApiModelProperty("用户微信登录唯一标识")
    @NotNull
    private String openId;

    @ApiModelProperty("微信头像地址")
    private String avatar;

    @ApiModelProperty("微信昵称")
    private String name;

    @ApiModelProperty("性别")
    private Integer gender;

}
