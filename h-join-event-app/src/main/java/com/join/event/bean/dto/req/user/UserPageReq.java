package com.join.event.bean.dto.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author ljh
 * @since 2025/3/17
 */
@Data
@ApiModel("用户通用请求实体")
public class UserPageReq {

    @ApiModelProperty("用户id")
    private Long userId;

}
