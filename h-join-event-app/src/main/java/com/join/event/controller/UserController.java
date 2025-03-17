package com.join.event.controller;


import com.join.event.bean.common.BaseResDto;
import com.join.event.bean.dto.req.UserPageReq;
import com.join.event.bean.dto.req.UserLoginReq;
import com.join.event.bean.dto.res.UserInfoRes;
import com.join.event.bean.dto.res.UserPageRes;
import com.join.event.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author ljh
 * @since 2025-03-14
 */
@RestController
@RequestMapping("/user")
@Api(value = "用户相关接口", tags = "用户相关接口")
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/login")
    @ApiOperation("登录并获取个人基础信息")
    public BaseResDto<UserInfoRes> login(@RequestBody UserLoginReq userLoginReq) {
        return new BaseResDto<>(userService.login(userLoginReq));
    }

    @PostMapping("/myPage")
    @ApiOperation("我的页面")
    public BaseResDto<UserPageRes> myPage(@RequestBody UserPageReq userPageReq) {
        return new BaseResDto<>(userService.myPage(userPageReq));
    }


}
