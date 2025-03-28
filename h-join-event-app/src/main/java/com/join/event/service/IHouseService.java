package com.join.event.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.join.event.bean.dto.req.header.HeaderActReq;
import com.join.event.bean.dto.req.header.HeaderHomePageReq;
import com.join.event.bean.dto.req.house.CheckHouseNumReq;
import com.join.event.bean.dto.req.house.CreateOrUpdateHouseReq;
import com.join.event.bean.dto.req.house.MyHouseReq;
import com.join.event.bean.dto.req.user.UserHomePageReq;
import com.join.event.bean.dto.res.header.HeaderHomePageRes;
import com.join.event.bean.dto.res.user.UserHouseRes;
import com.join.event.bean.dto.res.house.UserHomePageRes;
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

    IPage<UserHomePageRes> userHomePage(UserHomePageReq userHomePageReq);

    List<UserHouseRes> headerAct(HeaderActReq headerActReq);

    IPage<HeaderHomePageRes> headerHomePage(HeaderHomePageReq headerHomePageReq);
}
