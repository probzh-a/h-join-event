package com.join.event.service.impl;

import com.join.event.entity.UserLike;
import com.join.event.mapper.UserLikeMapper;
import com.join.event.service.IUserLikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户喜欢与邀请记录表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2025-03-14
 */
@Service
public class UserLikeServiceImpl extends ServiceImpl<UserLikeMapper, UserLike> implements IUserLikeService {

}
