package com.join.event.config.exception.define;

import lombok.Data;

/**
 * @author z1aoyu
 */
@Data
public class InvalidDto {
    private String path;

    private Object value;

    private String reason;
}
