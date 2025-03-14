package com.join.event.config.exception;


import com.google.common.collect.Lists;
import com.join.event.bean.common.BaseResDto;
import com.join.event.bean.constant.ServerEnvConstants;
import com.join.event.bean.enums.BaseStatusCodeEnum;
import com.join.event.config.exception.define.InvalidDto;
import com.join.event.config.exception.define.ServiceException;
import com.join.event.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @author z1aoyu
 */
@ControllerAdvice
@RestControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private Environment env;

    /**
     * 异常处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResDto<Object> exceptionHandler(Exception e) {
        BaseResDto<Object> br = new BaseResDto<>();
        br.setError(BaseStatusCodeEnum.B000001);
        log.error(e.getMessage(), e);
        return br;
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResDto<Object> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        BaseResDto<Object> br = new BaseResDto<>();
        log.error(e.getMessage(), e);
        //为了处理自定义自定义String反序列化器抛出的异常
        Throwable baseException = getBaseException(e);
        if (Objects.nonNull(baseException)) {
            br.setError(BaseStatusCodeEnum.B000004.getCode(), baseException.getMessage());
        } else {
            br.setError(BaseStatusCodeEnum.B000004.getCode(), e.getMessage());
        }
        return br;
    }

    private Throwable getBaseException(HttpMessageNotReadableException e) {
        Throwable cause = e.getCause();
        if (Objects.isNull(cause)) {
            return null;
        }
        Throwable mayBaseException = cause.getCause();
        if ((mayBaseException instanceof BaseException)) {
            return mayBaseException;
        }
        return null;
    }

    @ExceptionHandler(value = BaseException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResDto<Object> BaseExceptionHandler(BaseException e) {
        BaseResDto<Object> br = new BaseResDto<>();
        br.setError(e.getCode(), e.getMessage());
        log.error(e.getMessage(), e);
        return br;
    }

    /**
     * Service异常处理
     */
    @ExceptionHandler(value = ServiceException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResDto<Object> serviceExceptionHandler(ServiceException e) {
        BaseResDto<Object> br = new BaseResDto<>();
        br.setError(e.getCode(), e.getMessage());
        log.error(e.getMessage(), e);
        return br;
    }

    /**
     * 请求Body内字段没有通过注解校验（通过参数级@Valid 启用的参数校验）
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public BaseResDto<Object> ArgumentExceptionHandler(MethodArgumentNotValidException e) {
        List<InvalidDto> invalids = Lists.newArrayList();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            InvalidDto invalid = new InvalidDto();
            if (Boolean.FALSE.equals(fieldError.getRejectedValue())) {
            } else {
                invalid.setPath(fieldError.getField());
            }
            invalid.setValue(fieldError.getRejectedValue());
            invalid.setReason(fieldError.getDefaultMessage());
            invalids.add(invalid);
        }
        BaseResDto<Object> br = new BaseResDto<>();
        br.setError(BaseStatusCodeEnum.B000004.getCode(), invalids.get(0).getReason());
        String[] activeProfiles = env.getActiveProfiles();
        if (activeProfiles.length > 1) {
            log.warn("无法判断当前profile");
        } else if (activeProfiles.length == 1 && StringUtils.equalsAnyIgnoreCase(activeProfiles[0], ServerEnvConstants.DEV, ServerEnvConstants.TEST)) {
            br.setData(invalids);
        }
        log.error("参数校验异常：{}", JsonUtils.toJsonPrettily(invalids), e);
        return br;
    }

}
