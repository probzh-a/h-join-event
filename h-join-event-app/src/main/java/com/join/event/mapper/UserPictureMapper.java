package com.join.event.mapper;

import com.join.event.bean.entity.UserPicture;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户照片表 Mapper 接口
 * </p>
 *
 * @author ljh
 * @since 2025-03-14
 */
public interface UserPictureMapper extends BaseMapper<UserPicture> {

    int batchInsert(@Param("list") List<UserPicture> userPictures);
}
