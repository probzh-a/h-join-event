package com.join.event.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


/**
 * 活动状态枚举
 */
@Getter
@AllArgsConstructor
public enum ActStatusEnum implements EnumAncestor<Integer> {

    START(0, "未开始"),
    IN(1, "进行中"),
    END(2, "已结束")
    ;

    @JsonValue
    private final Integer code;

    private final String title;

    /**
     * 获取code对应的枚举
     */
    @JsonCreator
    public static ActStatusEnum of(Integer code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }


    @Override
    public String toString() {
        return asJavabean().toString();
    }
}
