package com.join.event.config.idFactory.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@ConfigurationProperties(prefix = "idworker.snowflake")
@Data
public class Snowflake {
    /**
     * 工作机器ID(0~31)
     */
    private Long workerId ;

    /**
     * workerId服务间可以设置的不一样，减少id冲突
     */
    @PostConstruct
    public void validate() {
        if (workerId == null) {
            throw new IllegalStateException(" [启动失败] workerId 未配置，请在 application.yml 或环境变量中指定 idwork.snowflake.workerId");
        }
    }

}

