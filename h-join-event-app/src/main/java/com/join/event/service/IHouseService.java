package com.join.event.service;

import com.join.event.bean.dto.req.house.CheckHouseNumReq;
import com.join.event.bean.dto.req.house.CreateOrUpdateHouseReq;
import com.join.event.bean.dto.req.house.MyHouseReq;
import com.join.event.bean.dto.res.user.UserHouseRes;
import com.join.event.bean.dto.res.user.UserPageRes;
import com.join.event.bean.entity.House;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 房间表 服务类
 * </p>
 *
 * @author ljh
 * @since 2025-03-14
 */
public interface IHouseService extends IService<House> {

    List<UserHouseRes> myAct(MyHouseReq myHouseReq);

    void checkHouseNum(CheckHouseNumReq checkHouseNumReq);

    void headCreateHouse(CreateOrUpdateHouseReq createOrUpdateHouseReq);
}
