package com.join.event.bean.dto.res;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author ljh
 * @since 2025/3/26
 */
@Data
public class UserPictureRes {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "地址")
    private String pictureUrl;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "照片类型 0-头像 1-普通照片 2-主页展示照片")
    private Integer type;

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
