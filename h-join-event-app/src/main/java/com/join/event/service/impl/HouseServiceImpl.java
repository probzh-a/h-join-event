package com.join.event.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.join.event.bean.dto.req.MyHouseReq;
import com.join.event.bean.dto.res.MyHouseRes;
import com.join.event.bean.entity.House;
import com.join.event.bean.entity.HouseUser;
import com.join.event.bean.enums.ActStatusEnum;
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
    public List<MyHouseRes> myAct(MyHouseReq myHouseReq) {
        Long userId = myHouseReq.getUserId();
        Boolean isMyRoom = myHouseReq.getIsMyRoom();

        ArrayList<MyHouseRes> myHouseResArrayList = new ArrayList<>();

        LambdaQueryWrapper<HouseUser> houseUserWrapper = new LambdaQueryWrapper<>();
        houseUserWrapper.eq(HouseUser::getUserId, userId);
        List<HouseUser> houseUsers = houseUserMapper.selectList(houseUserWrapper);
        List<Long> houseIds = houseUsers.stream().map(HouseUser::getHouseId).distinct().collect(Collectors.toList());
        if (!isMyRoom && CollectionUtil.isEmpty(houseIds)) {
            return myHouseResArrayList;
        }
        LambdaQueryWrapper<House> houseWrapper = new LambdaQueryWrapper<>();
        if (isMyRoom) {
            houseWrapper.eq(House::getHouseOwner, userId);
        } else {
            houseWrapper.in(CollectionUtil.isNotEmpty(houseIds), House::getId, houseIds);
        }
        List<House> houses = houseMapper.selectList(houseWrapper);
        if (CollectionUtil.isNotEmpty(houses)) {
            List<MyHouseRes> myHouseRes = BeanUtil.copyToList(houses, MyHouseRes.class);
            myHouseResArrayList.addAll(myHouseRes);
        }
        if (CollectionUtil.isEmpty(myHouseResArrayList)) {
            return myHouseResArrayList;
        }
        for (MyHouseRes myHouseRes : myHouseResArrayList) {
            getActStatus(myHouseRes);
        }
        return myHouseResArrayList;
    }

    private static void getActStatus(MyHouseRes myHouseRes) {
        LocalDateTime startTime = myHouseRes.getStartTime();
        LocalDateTime endTime = myHouseRes.getEndTime();
        if (LocalDateTime.now().isAfter(endTime)) {
            myHouseRes.setActStatus(ActStatusEnum.END.getCode());
        } else if (LocalDateTime.now().isBefore(startTime)) {
            myHouseRes.setActStatus(ActStatusEnum.START.getCode());
        } else {
            myHouseRes.setActStatus(ActStatusEnum.IN.getCode());
        }
    }
}
