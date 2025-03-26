package com.join.event.bean.dto.res;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ljh
 * @since 2025/3/26
 */
@Data
@ApiModel("用户局头信息")
public class UserHeadRes {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "评分 0-10")
    private Integer source;

    @ApiModelProperty(value = "组织次数")
    private Long orgNum;

    @ApiModelProperty(value = "背景图片id")
    private Long groundPicId;

    @ApiModelProperty(value = "背景图片url")
    private String groundPicUrl;

    @ApiModelProperty(value = "个人简介")
    private String summary;

    @ApiModelProperty("局头旗下头牌:只有局头会返回")
    private List<UserPictureRes> headPictureResList;
}
