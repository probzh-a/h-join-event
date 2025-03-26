package com.join.event.bean.dto.req.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@ApiModel("用户编辑请求实体")
public class UserUpdateReq {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty(value = "用户微信登录唯一标识")
    private String openId;

    @ApiModelProperty(value = "微信头像地址")
    private String avatar;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "性别 0-未知 1-男性 2-女性")
    private Integer gender;

    @ApiModelProperty(value = "个人简介")
    private String summary;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "出生日期")
    private LocalDate birthday;

    @ApiModelProperty(value = "用户照片列表")
    private List<UserPictureReq> userPictureReqs;

    @ApiModelProperty("局头特殊信息(只有局头需要填写)")
    private UserHeadReq userHeadReq;

}
