package com.join.event.bean.dto.res.header;

import com.join.event.bean.dto.res.user.UserInfoRes;
import com.join.event.bean.dto.res.user.UserPictureRes;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class HeaderHomePageRes {

    @ApiModelProperty("用户基础信息")
    private UserInfoRes userInfoRes;

    @ApiModelProperty("用户照片列表")
    private List<UserPictureRes> userPictureResList;

}
