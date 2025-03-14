package com.join.event.config.exception.define;

import com.join.event.config.exception.BaseException;
import com.join.event.bean.enums.EnumAncestor;

/**
 * @author z1aoyu 2024/11/13 2:52 下午
 */
public class ServiceException extends BaseException {

    private static final long serialVersionUID = 2202001654780470782L;

    public ServiceException(EnumAncestor<?> enumAncestor) {
        super(enumAncestor.getCode().toString(), enumAncestor.getTitle());
    }

    public ServiceException(EnumAncestor<?> enumAncestor,String message) {
        super(enumAncestor.getCode().toString(), message);
    }
}
