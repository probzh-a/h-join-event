package com.join.event.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


/**
 * 用户照片枚举
 */
@Getter
@AllArgsConstructor
public enum UserPictureTypeEnum implements EnumAncestor<Integer> {

    AVATAR(0, "头像"),
    NORMAL(1, "普通照片"),
    HOME_PAGE(2, "主页展示照片"),
    BACK_GROUND(3, "背景照片")
    ;

    @JsonValue
    private final Integer code;

    private final String title;

    /**
     * 获取code对应的枚举
     */
    @JsonCreator
    public static UserPictureTypeEnum of(Integer code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }


    @Override
    public String toString() {
        return asJavabean().toString();
    }
}
