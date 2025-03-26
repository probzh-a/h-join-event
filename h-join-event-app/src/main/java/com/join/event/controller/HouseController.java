package com.join.event.controller;


import com.join.event.service.IHouseService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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

}
