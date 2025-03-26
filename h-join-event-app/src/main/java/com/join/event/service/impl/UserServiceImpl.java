package com.join.event.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.join.event.bean.dto.req.house.MyHouseReq;
import com.join.event.bean.dto.req.user.*;
import com.join.event.bean.dto.res.user.*;
import com.join.event.bean.entity.User;
import com.join.event.bean.entity.UserHead;
import com.join.event.bean.entity.UserPicture;
import com.join.event.bean.enums.AuthorityEnum;
import com.join.event.bean.enums.BaseStatusCodeEnum;
import com.join.event.bean.enums.UserPictureTypeEnum;
import com.join.event.config.exception.define.ServiceException;
import com.join.event.config.idFactory.Idworker;
import com.join.event.mapper.UserHeadMapper;
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
import java.util.Arrays;
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
    UserHeadMapper userHeadMapper;

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

        //用户局头信息
        UserHeadRes userHeadRes = userIsHeadOrNo(userId);
        if (null != userHeadRes) {
            userPageRes.setUserHeadRes(userHeadRes);
        }

        //用户照片
        LambdaQueryWrapper<UserPicture> userPictureLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userPictureLambdaQueryWrapper.eq(UserPicture::getUserId, userId);
        userPictureLambdaQueryWrapper.eq(UserPicture::getType, UserPictureTypeEnum.NORMAL.getCode());
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

    public UserHeadRes userIsHeadOrNo(Long userId) {
        UserHeadRes userHeadRes = new UserHeadRes();
        LambdaQueryWrapper<UserHead> userHeadLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userHeadLambdaQueryWrapper.eq(UserHead::getUserId, userId);
        List<UserHead> userHeads = userHeadMapper.selectList(userHeadLambdaQueryWrapper);
        if (CollectionUtil.isNotEmpty(userHeads)) {
            UserHead userHead = userHeads.stream().findFirst().orElse(null);
            Long groundPicId = userHead.getGroundPicId();
            if (null != groundPicId) {
                UserPicture userPicture = userPictureMapper.selectById(groundPicId);
                userHeadRes.setGroundPicUrl(userPicture.getPictureUrl());
            }
            BeanUtil.copyProperties(userHead, userHeadRes);
            LambdaQueryWrapper<UserPicture> userPictureLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userPictureLambdaQueryWrapper.eq(UserPicture::getUserId, userHead);
            userPictureLambdaQueryWrapper.eq(UserPicture::getType, UserPictureTypeEnum.HOME_PAGE.getCode());
            List<UserPicture> userPictures = userPictureMapper.selectList(userPictureLambdaQueryWrapper);
            if (CollectionUtil.isNotEmpty(userPictures)) {
                userHeadRes.setHeadPictureResList(BeanUtil.copyToList(userPictures, UserPictureRes.class));
            }
            return userHeadRes;
        } else {
            return null;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(UserUpdateReq userUpdateReq) {
        User user = BeanUtil.copyProperties(userUpdateReq, User.class);
        user.setUpdatedTime(LocalDateTime.now());

        List<UserPicture> userPicturesSave = new ArrayList<>();

        LambdaQueryWrapper<UserPicture> userPictureLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userPictureLambdaQueryWrapper.eq(UserPicture::getUserId, userUpdateReq.getId());
        userPictureLambdaQueryWrapper.in(UserPicture::getType, Arrays.asList(UserPictureTypeEnum.NORMAL.getCode(), UserPictureTypeEnum.AVATAR.getCode()));
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
        //更改局头信息
        if (null != userUpdateReq.getUserHeadReq()) {
            UserHeadReq userHeadReq = userUpdateReq.getUserHeadReq();
            UserHead userHead = BeanUtil.copyProperties(userHeadReq, UserHead.class);
            userHead.setUpdatedTime(LocalDateTime.now());
            userHeadMapper.updateById(userHead);

            LambdaQueryWrapper<UserPicture> headPictureLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userPictureLambdaQueryWrapper.eq(UserPicture::getUserId, userUpdateReq.getId());
            userPictureLambdaQueryWrapper.in(UserPicture::getType, UserPictureTypeEnum.HOME_PAGE.getCode());
            userPictureMapper.delete(headPictureLambdaQueryWrapper);

            List<UserPictureReq> headPictureResList = userHeadReq.getHeadPictureResList();
            if (CollectionUtil.isNotEmpty(headPictureResList)) {
                List<UserPicture> userPictures = BeanUtil.copyToList(headPictureResList, UserPicture.class);
                userPictureMapper.batchInsert(userPictures);
            }
        }

    }
}
