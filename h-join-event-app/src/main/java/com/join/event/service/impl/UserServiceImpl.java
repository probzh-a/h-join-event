package com.join.event.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.join.event.bean.dto.req.UserLoginReq;
import com.join.event.bean.dto.res.UserInfoRes;
import com.join.event.bean.entity.User;
import com.join.event.bean.enums.AuthorityEnum;
import com.join.event.bean.enums.BaseStatusCodeEnum;
import com.join.event.config.exception.define.ServiceException;
import com.join.event.mapper.UserMapper;
import com.join.event.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.join.event.util.JsonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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

    @Resource
    private UserMapper userMapper;

    @Override
    public UserInfoRes login(UserLoginReq userLoginReq) {
        if (null == userLoginReq.getOpenId()) {
            throw new ServiceException(BaseStatusCodeEnum.B000004, "登录人唯一标识为空");
        }
        // 判断是否为首次登录
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getOpenId, userLoginReq.getOpenId());
        User user = userMapper.selectList(userLambdaQueryWrapper).stream().findFirst().orElse(null);

        if (null == user) {
            //首次登录

            User newUser =  JsonUtils.toObject(JSONU,User.class);
            user.setCreatedTime(LocalDateTime.now());
            user.setAuthority(AuthorityEnum.NORMAL.getCode());
        } else {

        }

        return null;
    }
}
