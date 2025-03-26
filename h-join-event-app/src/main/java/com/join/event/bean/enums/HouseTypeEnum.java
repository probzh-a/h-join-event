package com.join.event.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


/**
 * 房间类型枚举
 */
@Getter
@AllArgsConstructor
public enum HouseTypeEnum implements EnumAncestor<Integer> {

    NORMAL(0, "普通房间"),
    ;

    @JsonValue
    private final Integer code;

    private final String title;

    /**
     * 获取code对应的枚举
     */
    @JsonCreator
    public static HouseTypeEnum of(Integer code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }


    @Override
    public String toString() {
        return asJavabean().toString();
    }
}
