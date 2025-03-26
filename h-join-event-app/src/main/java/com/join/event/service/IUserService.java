package com.join.event.service;

import com.join.event.bean.dto.req.user.UserPageReq;
import com.join.event.bean.dto.req.user.UserLoginReq;
import com.join.event.bean.dto.req.user.UserUpdateReq;
import com.join.event.bean.dto.res.user.UserInfoRes;
import com.join.event.bean.dto.res.user.UserPageRes;
import com.join.event.bean.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ljh
 * @since 2025-03-14
 */
public interface IUserService extends IService<User> {

    UserInfoRes login(UserLoginReq userLoginReq);

    UserPageRes myPage(UserPageReq userPageReq);

    void updateUserInfo(UserUpdateReq userUpdateReq);
}
