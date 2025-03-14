package com.join.event.bean.common;

import com.join.event.bean.enums.BaseStatusCodeEnum;
import com.join.event.bean.enums.EnumAncestor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
/**
 * 消息体
 *
 * @author z1aoyu
 */
public class BaseResDto<E> implements Serializable {

    private static final long serialVersionUID = 8165701091155726308L;

    protected boolean flag = true; // 调用结果标识 false:失败 true:成功

    protected String statusCode = BaseStatusCodeEnum.B000000.getCode();// 状态编码

    protected String message = BaseStatusCodeEnum.B000000.getTitle();// 状态信息

    protected E data;// 返回的内容

    public boolean isFlag() {
        return BaseStatusCodeEnum.B000000.getCode().equals(statusCode);
    }

    public BaseResDto() {
        isFlag();
    }

    public BaseResDto(E data) {
        this.data = data;
        isFlag();
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public void setError(String code, String msg) {
        this.statusCode = code;
        this.message = msg;
        isFlag();
    }

    public void setError(EnumAncestor<?> baseEnum) {
        this.statusCode = baseEnum.getCode().toString();
        this.message = baseEnum.getTitle();
        isFlag();
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

}
