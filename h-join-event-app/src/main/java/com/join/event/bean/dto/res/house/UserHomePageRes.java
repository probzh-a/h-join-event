package com.join.event.bean.dto.res.house;

import com.join.event.bean.dto.res.user.InUserRes;
import com.join.event.bean.dto.res.user.UserInfoRes;
import com.join.event.bean.dto.res.user.UserPictureRes;
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
 * @since 2025/3/27
 */
@Data
public class UserHomePageRes {

    @ApiModelProperty("活动id")
    private Long id;
    @ApiModelProperty(value = "房间号")
    private Long number;
    @ApiModelProperty(value = "房间名称")
    private String name;
    @ApiModelProperty(value = "活动状态 0-未开始 1-进行中 2-已结束")
    private Integer actStatus;
    @ApiModelProperty(value = "活动开始时间")
    private LocalDateTime startTime;
    @ApiModelProperty(value = "活动结束时间")
    private LocalDateTime endTime;
    @ApiModelProperty(value = "预定活动人数")
    private Long personNum;
    @ApiModelProperty(value = "参加活动人数")
    private Long inPersonNum;

    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "用户头像地址")
    private String userAvatar;
    @ApiModelProperty(value = "局头被点赞数量")
    private Integer likeNum;

    @ApiModelProperty("参与的用户照片列表")
    private List<InUserRes> userJoinPicList;

}
