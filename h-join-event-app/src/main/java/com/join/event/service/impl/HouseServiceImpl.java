package com.join.event.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.join.event.bean.dto.req.header.HeaderActReq;
import com.join.event.bean.dto.req.header.HeaderHomePageReq;
import com.join.event.bean.dto.req.house.CheckHouseNumReq;
import com.join.event.bean.dto.req.house.CreateOrUpdateHouseReq;
import com.join.event.bean.dto.req.house.MyHouseReq;
import com.join.event.bean.dto.req.user.UserHomePageReq;
import com.join.event.bean.dto.res.header.HeaderHomePageRes;
import com.join.event.bean.dto.res.user.InUserRes;
import com.join.event.bean.dto.res.user.UserHouseRes;
import com.join.event.bean.dto.res.house.UserHomePageRes;
import com.join.event.bean.dto.res.user.UserInfoRes;
import com.join.event.bean.dto.res.user.UserPictureRes;
import com.join.event.bean.entity.*;
import com.join.event.bean.enums.ActStatusEnum;
import com.join.event.bean.enums.AuthorityEnum;
import com.join.event.bean.enums.BaseStatusCodeEnum;
import com.join.event.bean.enums.UserPictureTypeEnum;
import com.join.event.config.exception.define.ServiceException;
import com.join.event.mapper.*;
import com.join.event.service.IHouseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 房间表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2025-03-14
 */
@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements IHouseService {

    @Resource
    private HouseMapper houseMapper;
    @Resource
    private HouseUserMapper houseUserMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserHeadMapper userHeadMapper;
    @Resource
    private UserServiceImpl userService;
    @Resource
    private UserPictureMapper userPictureMapper;

    @Override
    public List<UserHouseRes> myAct(MyHouseReq myHouseReq) {
        Long userId = myHouseReq.getUserId();
        Boolean isMyRoom = myHouseReq.getIsMyRoom();

        ArrayList<UserHouseRes> userHouseResArrayList = new ArrayList<>();

        LambdaQueryWrapper<HouseUser> houseUserWrapper = new LambdaQueryWrapper<>();
        houseUserWrapper.eq(HouseUser::getUserId, userId);
        List<HouseUser> houseUsers = houseUserMapper.selectList(houseUserWrapper);
        List<Long> houseIds = houseUsers.stream().map(HouseUser::getHouseId).distinct().collect(Collectors.toList());
        if (!isMyRoom && CollectionUtil.isEmpty(houseIds)) {
            return userHouseResArrayList;
        }
        LambdaQueryWrapper<House> houseWrapper = new LambdaQueryWrapper<>();
        if (isMyRoom) {
            houseWrapper.eq(House::getHouseOwner, userId);
        } else {
            houseWrapper.in(CollectionUtil.isNotEmpty(houseIds), House::getId, houseIds);
        }
        List<House> houses = houseMapper.selectList(houseWrapper);
        if (CollectionUtil.isNotEmpty(houses)) {
            List<UserHouseRes> userHouseRes = BeanUtil.copyToList(houses, UserHouseRes.class);
            userHouseResArrayList.addAll(userHouseRes);
        }
        if (CollectionUtil.isEmpty(userHouseResArrayList)) {
            return userHouseResArrayList;
        }
        for (UserHouseRes userHouseRes : userHouseResArrayList) {
            getActStatus(userHouseRes);
        }
        return userHouseResArrayList;
    }

    @Override
    public void checkHouseNum(CheckHouseNumReq checkHouseNumReq) {
        LambdaQueryWrapper<House> houseLambdaQueryWrapper = new LambdaQueryWrapper<>();
        houseLambdaQueryWrapper.eq(House::getNumber, checkHouseNumReq.getHouseNum());
        houseLambdaQueryWrapper.ge(House::getStartTime, LocalDateTime.now());
        Long count = houseMapper.selectCount(houseLambdaQueryWrapper);
        if (count >= 0) {
            throw new ServiceException(BaseStatusCodeEnum.B000004, "房间编号重复");
        }
    }

    @Override
    public void headCreateHouse(CreateOrUpdateHouseReq createOrUpdateHouseReq) {
        House house = BeanUtil.copyProperties(createOrUpdateHouseReq, House.class);
        house.setUpdatedTime(LocalDateTime.now());
        this.saveOrUpdate(house);
    }

    @Override
    public IPage<UserHomePageRes> userHomePage(UserHomePageReq req) {
        IPage<House> housePage = new Page<>(req.getPageNum(), req.getPageSize());
        LambdaQueryWrapper<House> houseLambdaQueryWrapper = new LambdaQueryWrapper<>();
        houseLambdaQueryWrapper.ge(House::getEndTime, LocalDateTime.now());
        houseLambdaQueryWrapper.orderByDesc(House::getStartTime);
        IPage<House> pageList = this.page(housePage, houseLambdaQueryWrapper);
        List<House> records = pageList.getRecords();

        IPage<UserHomePageRes> result = new Page<>(
                pageList.getCurrent(),
                pageList.getSize(),
                pageList.getTotal()
        );

        List<Long> houseIds = records.stream().map(House::getId).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(houseIds)) {
            return result;
        }

        LambdaQueryWrapper<HouseUser> houseUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        houseUserLambdaQueryWrapper.in(HouseUser::getHouseId, houseIds);
        List<HouseUser> houseUserList = houseUserMapper.selectList(houseUserLambdaQueryWrapper);
        Map<Long, List<HouseUser>> houseUserMap = houseUserList.stream().collect(Collectors.groupingBy(HouseUser::getHouseId));

        List<Long> headUserIds = records.stream().map(House::getHouseOwner).collect(Collectors.toList());
        List<Long> inUserList = houseUserList.stream().map(HouseUser::getUserId).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(inUserList)) {
            headUserIds.addAll(inUserList);
        }
        List<User> users = userMapper.selectBatchIds(headUserIds);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, Function.identity()));

        LambdaQueryWrapper<UserHead> userHeadLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userHeadLambdaQueryWrapper.in(UserHead::getUserId, headUserIds);
        List<UserHead> userHeads = userHeadMapper.selectList(userHeadLambdaQueryWrapper);
        Map<Long, UserHead> userHeadMap = userHeads.stream().collect(Collectors.toMap(UserHead::getUserId, Function.identity()));


        if (CollectionUtil.isNotEmpty(records)) {
            ArrayList<UserHomePageRes> userHomePageResArrayList = new ArrayList<>();
            for (House record : records) {
                getHouseInfo(record, houseUserMap, userMap, userHeadMap, userHomePageResArrayList);
            }
            result.setRecords(userHomePageResArrayList);
            result.setPages(pageList.getPages());
        }
        return result;
    }

    @Override
    public List<UserHouseRes> headerAct(HeaderActReq headerActReq) {
        Long userId = headerActReq.getUserId();
        LambdaQueryWrapper<House> houseLambdaQueryWrapper = new LambdaQueryWrapper<>();
        houseLambdaQueryWrapper.eq(House::getHouseOwner, userId);
        houseLambdaQueryWrapper.ge(House::getEndTime, LocalDateTime.now());
        List<House> houses = houseMapper.selectList(houseLambdaQueryWrapper);
        return BeanUtil.copyToList(houses, UserHouseRes.class);
    }

    @Override
    public IPage<HeaderHomePageRes> headerHomePage(HeaderHomePageReq req) {
        IPage<User> userPage = new Page<>(req.getPageNum(), req.getPageSize());
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.ge(User::getAuthority, AuthorityEnum.NORMAL.getCode());
        userLambdaQueryWrapper.orderByDesc(User::getInvitedNum);
        IPage<User> pageList = userService.page(userPage, userLambdaQueryWrapper);
        List<User> records = pageList.getRecords();

        IPage<HeaderHomePageRes> result = new Page<>(
                pageList.getCurrent(),
                pageList.getSize(),
                pageList.getTotal()
        );
        if (CollectionUtil.isEmpty(records)) {
            return result;
        }
        List<Long> userIds = records.stream().map(User::getId).collect(Collectors.toList());
        LambdaQueryWrapper<UserPicture> userPictureLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userPictureLambdaQueryWrapper.in(UserPicture::getUserId, userIds);
        userPictureLambdaQueryWrapper.in(UserPicture::getType, Arrays.asList(UserPictureTypeEnum.AVATAR.getCode(), UserPictureTypeEnum.NORMAL.getCode()));
        userPictureLambdaQueryWrapper.orderByAsc(UserPicture::getSort);
        List<UserPicture> userPicturesList = userPictureMapper.selectList(userPictureLambdaQueryWrapper);
        Map<Long, List<UserPicture>> userPicMap = userPicturesList.stream().collect(Collectors.groupingBy(UserPicture::getUserId));
        ArrayList<HeaderHomePageRes> headerHomePageResArrayList = new ArrayList<>();
        for (User record : records) {
            HeaderHomePageRes headerHomePageRes = new HeaderHomePageRes();
            UserInfoRes userInfoRes = BeanUtil.copyProperties(record, UserInfoRes.class);
            headerHomePageRes.setUserInfoRes(userInfoRes);
            List<UserPicture> userPictures = userPicMap.get(record.getId());
            List<UserPictureRes> userPictureRes = BeanUtil.copyToList(userPictures, UserPictureRes.class);
            headerHomePageRes.setUserPictureResList(userPictureRes);
            headerHomePageResArrayList.add(headerHomePageRes);
        }
        result.setRecords(headerHomePageResArrayList);
        result.setPages(pageList.getPages());
        return result;
    }

    private static void getHouseInfo(House record,
                                     Map<Long, List<HouseUser>> houseUserMap,
                                     Map<Long, User> userMap,
                                     Map<Long, UserHead> userHeadMap,
                                     ArrayList<UserHomePageRes> userHomePageResArrayList) {
        //活动基础信息
        UserHomePageRes userHomePageRes = BeanUtil.copyProperties(record, UserHomePageRes.class);
        UserHouseRes userHouseRes = BeanUtil.copyProperties(userHomePageRes, UserHouseRes.class);
        getActStatus(userHouseRes);
        userHomePageRes.setActStatus(userHouseRes.getActStatus());
        //参与活动人数
        List<HouseUser> houseUsers = houseUserMap.get(record.getId());
        userHomePageRes.setInPersonNum((long) houseUsers.size());
        //局头信息
        User user = userMap.get(record.getHouseOwner());
        userHomePageRes.setUserName(user.getName());
        userHomePageRes.setUserAvatar(user.getAvatar());
        UserHead userHead = userHeadMap.get(user.getId());
        userHomePageRes.setLikeNum(userHead.getLikeNum());
        //参与用户信息
        ArrayList<InUserRes> inUserResList = new ArrayList<>();
        for (HouseUser houseUser : houseUsers) {
            InUserRes inUserRes = new InUserRes();
            User inUser = userMap.get(houseUser.getUserId());
            inUserRes.setUserId(inUser.getId());
            inUserRes.setUserName(inUserRes.getUserName());
            inUserRes.setAvatar(inUser.getAvatar());
            inUserResList.add(inUserRes);
        }
        userHomePageRes.setUserJoinPicList(inUserResList);
        userHomePageResArrayList.add(userHomePageRes);
    }

    private static void getActStatus(UserHouseRes userHouseRes) {
        LocalDateTime startTime = userHouseRes.getStartTime();
        LocalDateTime endTime = userHouseRes.getEndTime();
        if (LocalDateTime.now().isAfter(endTime)) {
            userHouseRes.setActStatus(ActStatusEnum.END.getCode());
        } else if (LocalDateTime.now().isBefore(startTime)) {
            userHouseRes.setActStatus(ActStatusEnum.START.getCode());
        } else {
            userHouseRes.setActStatus(ActStatusEnum.IN.getCode());
        }
    }
}
