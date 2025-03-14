package com.join.event.service.impl;

import com.join.event.bean.dto.req.UserLoginReq;
import com.join.event.bean.dto.res.UserInfoRes;
import com.join.event.bean.entity.User;
import com.join.event.bean.enums.BaseStatusCodeEnum;
import com.join.event.config.exception.BaseException;
import com.join.event.config.exception.define.ServiceException;
import com.join.event.mapper.UserMapper;
import com.join.event.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2025-03-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public UserInfoRes login(UserLoginReq userLoginReq) {
        throw new ServiceException(BaseStatusCodeEnum.B000004, "已配对！");
    }
}
