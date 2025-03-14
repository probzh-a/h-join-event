package com.join.event.bean.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 用户喜欢与邀请记录表
 * </p>
 *
 * @author ljh
 * @since 2025-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_like")
@ApiModel(value="UserLike对象", description="用户喜欢与邀请记录表")
public class UserLike implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "发起人")
    private Long userId;

    @ApiModelProperty(value = "接受人")
    private Long toUserId;

    @ApiModelProperty(value = "房间号")
    private Long number;

    @ApiModelProperty(value = "0-喜欢 1-邀请")
    private Integer type;

    @ApiModelProperty(value = "是否接受 0-否 1-是")
    private Integer isAccept;

    @ApiModelProperty(value = "留言/备注")
    private String message;

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
