package com.join.event.bean.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;


/**
 * 用户角色分类枚举
 */
@Getter
@AllArgsConstructor
public enum AuthorityEnum implements EnumAncestor<Integer> {

    NORMAL(0, "普通用户"),
    HEADER(1, "局头"),
    ;

    @JsonValue
    private final Integer code;

    private final String title;

    /**
     * 获取code对应的枚举
     */
    @JsonCreator
    public static AuthorityEnum of(Integer code) {
        return Arrays.stream(values()).filter(anEnum -> anEnum.getCode().equals(code)).findFirst().orElse(null);
    }


    @Override
    public String toString() {
        return asJavabean().toString();
    }
}
