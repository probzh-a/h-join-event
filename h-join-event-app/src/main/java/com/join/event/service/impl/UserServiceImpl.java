package com.join.event.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.join.event.bean.dto.req.*;
import com.join.event.bean.dto.res.UserHouseRes;
import com.join.event.bean.dto.res.UserInfoRes;
import com.join.event.bean.dto.res.UserPageRes;
import com.join.event.bean.dto.res.UserPictureRes;
import com.join.event.bean.entity.User;
import com.join.event.bean.entity.UserPicture;
import com.join.event.bean.enums.AuthorityEnum;
import com.join.event.bean.enums.BaseStatusCodeEnum;
import com.join.event.bean.enums.UserPictureTypeEnum;
import com.join.event.config.exception.define.ServiceException;
import com.join.event.config.idFactory.Idworker;
import com.join.event.mapper.UserMapper;
import com.join.event.mapper.UserPictureMapper;
import com.join.event.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    UserPictureMapper userPictureMapper;
    @Resource
    HouseServiceImpl houseService;
    @Resource
    TransactionTemplate transactionTemplate;

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

    @Override
    public UserPageRes myPage(UserPageReq userPageReq) {
        UserPageRes userPageRes = new UserPageRes();

        //用户基础信息
        Long userId = userPageReq.getUserId();
        User user = userMapper.selectById(userId);
        UserInfoRes userInfoRes = BeanUtil.copyProperties(user, UserInfoRes.class);
        userPageRes.setUserInfoRes(userInfoRes);

        //用户照片
        LambdaQueryWrapper<UserPicture> userPictureLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userPictureLambdaQueryWrapper.eq(UserPicture::getUserId, userId);
        List<UserPicture> userPictures = userPictureMapper.selectList(userPictureLambdaQueryWrapper);
        List<UserPictureRes> userPictureRes = BeanUtil.copyToList(userPictures, UserPictureRes.class);
        userPageRes.setUserPictureResList(userPictureRes);

        //用户活动
        MyHouseReq myHouseReq = new MyHouseReq();
        myHouseReq.setUserId(userId);
        myHouseReq.setIsMyRoom(Boolean.TRUE);
        List<UserHouseRes> userHouseRes = houseService.myAct(myHouseReq);
        userPageRes.setUserHouseResList(userHouseRes);

        return userPageRes;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(UserUpdateReq userUpdateReq) {
        User user = BeanUtil.copyProperties(userUpdateReq, User.class);
        user.setUpdatedTime(LocalDateTime.now());

        List<UserPicture> userPicturesSave = new ArrayList<>();

        LambdaQueryWrapper<UserPicture> userPictureLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userPictureLambdaQueryWrapper.eq(UserPicture::getUserId, userUpdateReq.getId());
        userPictureMapper.delete(userPictureLambdaQueryWrapper);

        List<UserPictureReq> userPictureReqs = userUpdateReq.getUserPictureReqs();
        if (CollectionUtil.isNotEmpty(userPictureReqs)) {
            for (UserPictureReq userPictureReq : userPictureReqs) {
                UserPicture userPicture = BeanUtil.copyProperties(userPictureReq, UserPicture.class);
                if (0 == userPicture.getSort()) {
                    userPicture.setType(UserPictureTypeEnum.AVATAR.getCode());
                } else {
                    userPicture.setType(UserPictureTypeEnum.NORMAL.getCode());
                }
                userPicture.setId(idWorker.nextId());
                userPicture.setUserId(userUpdateReq.getId());
                userPicture.setUserId(userPicture.getUserId());
                userPicture.setCreatedTime(LocalDateTime.now());
                userPicturesSave.add(userPicture);
            }
        }
        userMapper.updateById(user);
        if (CollectionUtil.isNotEmpty(userPicturesSave)) {
            userPictureMapper.batchInsert(userPicturesSave);
        }
    }
}
