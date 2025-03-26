package com.join.event.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.join.event.bean.dto.req.house.CheckHouseNumReq;
import com.join.event.bean.dto.req.house.CreateOrUpdateHouseReq;
import com.join.event.bean.dto.req.house.MyHouseReq;
import com.join.event.bean.dto.res.user.UserHouseRes;
import com.join.event.bean.dto.res.user.UserPageRes;
import com.join.event.bean.entity.House;
import com.join.event.bean.entity.HouseUser;
import com.join.event.bean.enums.ActStatusEnum;
import com.join.event.bean.enums.BaseStatusCodeEnum;
import com.join.event.config.exception.define.ServiceException;
import com.join.event.mapper.HouseMapper;
import com.join.event.mapper.HouseUserMapper;
import com.join.event.service.IHouseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
