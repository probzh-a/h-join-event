package com.join.event.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.join.event.bean.dto.req.UserLoginReq;
import com.join.event.bean.dto.res.UserInfoRes;
import com.join.event.bean.entity.User;
import com.join.event.bean.enums.AuthorityEnum;
import com.join.event.bean.enums.BaseStatusCodeEnum;
import com.join.event.config.exception.define.ServiceException;
import com.join.event.config.idFactory.Idworker;
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
    UserMapper userMapper;
    @Resource
    Idworker idWorker;

    @Override
    public UserInfoRes login(UserLoginReq userLoginReq) {

        if (null == userLoginReq.getOpenId()) {
            throw new ServiceException(BaseStatusCodeEnum.B000004, "登录人唯一标识为空");
        }
        // 判断是否为首次登录
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getOpenId, userLoginReq.getOpenId());
        User user = userMapper.selectList(userLambdaQueryWrapper).stream().findFirst().orElse(null);

        //首次登录
        if (null == user) {
            User newUser = BeanUtil.copyProperties(userLoginReq, User.class);
            newUser.setId(idWorker.nextId());
            newUser.setCreatedTime(LocalDateTime.now());
            newUser.setAuthority(AuthorityEnum.NORMAL.getCode());
            return BeanUtil.copyProperties(newUser, UserInfoRes.class);
        } else {
            return BeanUtil.copyProperties(user, UserInfoRes.class);
        }
    }
}
