package com.join.event.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author ljh
 * @since 2025-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
@ApiModel(value="User对象", description="用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "性别 0-男 1-女")
    private Integer gender;

    @ApiModelProperty(value = "是否实名认证 0-否 1-是")
    private Integer isReal;

    @ApiModelProperty(value = "是否vip 0-否 1-是")
    private Integer isVip;

    @ApiModelProperty(value = "是否局头 0-否 1-是")
    private Integer isHead;

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

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "出生日期")
    private LocalDate birthday;

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
