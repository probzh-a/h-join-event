package com.join.event.bean.dto.req;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author ljh
 * @since 2025/3/18
 */
@Data
@ApiModel("用户照片请求实体")
public class UserPictureReq {

    @ApiModelProperty(value = "地址")
    private String pictureUrl;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "照片类型 0-头像 1-普通照片 2-主页展示照片")
    private Integer type;
}
