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
 * 用户局头表
 * </p>
 *
 * @author ljh
 * @since 2025-03-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_head")
@ApiModel(value="UserHead对象", description="用户局头表")
public class UserHead implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "评分 0-10")
    private Integer source;

    @ApiModelProperty(value = "组织次数")
    private Long orgNum;

    @ApiModelProperty(value = "背景图片id")
    private Long groundPicId;

    @ApiModelProperty(value = "个人简介")
    private String summary;

    @ApiModelProperty(value = "被点赞数量")
    private Integer likeNum;

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
