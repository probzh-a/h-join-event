package com.join.event.controller;


import com.join.event.bean.common.BaseResDto;
import com.join.event.bean.dto.req.MyHouseReq;
import com.join.event.bean.dto.req.UserPageReq;
import com.join.event.bean.dto.res.MyHouseRes;
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

    @PostMapping("/myAct")
    @ApiOperation("我的活动")
    public BaseResDto<List<MyHouseRes>> myAct(@RequestBody MyHouseReq myHouseReq) {
        return new BaseResDto<>(houseService.myAct(myHouseReq));
    }

}
