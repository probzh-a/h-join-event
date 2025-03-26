package com.join.event.bean.dto.req.house;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 *
 * </p>
 *
 * @author ljh
 * @since 2025/3/26
 */
@Data
public class CheckHouseNumReq {

    @NotNull
    @ApiModelProperty("房间编号")
    private Long houseNum;

}
