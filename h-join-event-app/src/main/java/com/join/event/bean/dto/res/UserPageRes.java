package com.join.event.bean.dto.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ljh
 * @since 2025/3/17
 */
@Data
@ApiModel("我的页面响应实体")
public class UserPageRes {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty(value = "用户微信登录唯一标识")
    private String openId;

    @ApiModelProperty(value = "微信头像地址")
    private String avatar;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "性别 0-未知 1-男性 2-女性")
    private Integer gender;

    @ApiModelProperty(value = "是否在线 0-否 1-是")
    private Boolean isOnline;

    @ApiModelProperty(value = "是否实名认证 0-否 1-是")
    private Boolean isReal;

    @ApiModelProperty(value = "是否vip 0-否 1-是")
    private Boolean isVip;

    @ApiModelProperty(value = "用户枚举类型")
    private Integer authority;

    @ApiModelProperty(value = "评价等级")
    private Integer level;

    @ApiModelProperty(value = "发起邀约数量")
    private Long inviteNum;

    @ApiModelProperty(value = "被邀约数量")
    private Long invitedNum;

    @ApiModelProperty(value = "喜欢数量")
    private Long likeNum;

    @ApiModelProperty(value = "被喜欢数量")
    private Long likedNum;

    @ApiModelProperty(value = "个人简介")
    private String summary;

}
