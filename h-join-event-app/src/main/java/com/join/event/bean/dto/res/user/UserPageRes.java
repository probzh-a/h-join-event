package com.join.event.bean.dto.res.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author ljh
 * @since 2025/3/17
 */
@Data
@ApiModel("我的页面响应实体")
public class UserPageRes {

    @ApiModelProperty("用户基础信息")
    private UserInfoRes userInfoRes;

    @ApiModelProperty("用户局头信息:只有局头才会返回")
    private UserHeadRes userHeadRes;

    @ApiModelProperty("用户照片列表")
    private List<UserPictureRes> userPictureResList;

    @ApiModelProperty("用户活动列表")
    private List<UserHouseRes> userHouseResList;

}
