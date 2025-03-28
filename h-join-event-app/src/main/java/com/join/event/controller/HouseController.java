package com.join.event.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.join.event.bean.common.BaseResDto;
import com.join.event.bean.dto.req.header.HeaderActReq;
import com.join.event.bean.dto.req.header.HeaderHomePageReq;
import com.join.event.bean.dto.req.house.CheckHouseNumReq;
import com.join.event.bean.dto.req.house.CreateOrUpdateHouseReq;
import com.join.event.bean.dto.req.user.UserHomePageReq;
import com.join.event.bean.dto.res.header.HeaderHomePageRes;
import com.join.event.bean.dto.res.user.UserHouseRes;
import com.join.event.bean.dto.res.house.UserHomePageRes;
import com.join.event.service.IHouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 房间表 前端控制器
 * </p>
 *
 * @author ljh
 * @since 2025-03-14
 */
@RestController
@RequestMapping("/house")
@Api(value = "房间相关接口", tags = "房间相关接口")
public class HouseController {

    @Resource
    IHouseService houseService;

    @PostMapping("/checkHouseNum")
    @ApiOperation("校验房间号重复")
    public BaseResDto<Void> checkHouseNum(@RequestBody CheckHouseNumReq checkHouseNumReq) {
        houseService.checkHouseNum(checkHouseNumReq);
        return new BaseResDto<>();
    }

    @PostMapping("/headCreateHouse")
    @ApiOperation("局头创建/修改房间")
    public BaseResDto<String> headCreateHouse(@RequestBody CreateOrUpdateHouseReq createOrUpdateHouseReq) {
        houseService.headCreateHouse(createOrUpdateHouseReq);
        return new BaseResDto<>("创建成功");
    }

    @PostMapping("/userHomePage")
    @ApiOperation("普通用户首页:活动列表")
    public BaseResDto<IPage<UserHomePageRes>> userHomePage(@RequestBody UserHomePageReq userHomePageReq) {
        return new BaseResDto<>(houseService.userHomePage(userHomePageReq));
    }

    @PostMapping("/headerHomePage")
    @ApiOperation("局头首页:人员列表")
    public BaseResDto<IPage<HeaderHomePageRes>> headerHomePage(@RequestBody HeaderHomePageReq headerHomePageReq) {
        return new BaseResDto<>(houseService.headerHomePage(headerHomePageReq));
    }


    @PostMapping("/headerAct")
    @ApiOperation("局头活动列表")
    public BaseResDto<List<UserHouseRes>> headerAct(@RequestBody HeaderActReq headerActReq) {
        return new BaseResDto<>(houseService.headerAct(headerActReq));
    }
}
