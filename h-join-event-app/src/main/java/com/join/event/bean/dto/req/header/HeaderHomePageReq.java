package com.join.event.bean.dto.req.header;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author ljh
 * @since 2025/3/27
 */
@Data
public class HeaderHomePageReq {

    @ApiModelProperty("当前用户id")
    private Long userId;

    @ApiModelProperty("pageNum")
    private Integer pageNum;

    @ApiModelProperty("pageSize")
    private Integer pageSize;

}

