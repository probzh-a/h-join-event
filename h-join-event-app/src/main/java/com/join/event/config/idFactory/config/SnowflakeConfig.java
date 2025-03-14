package com.join.event.config.idFactory.config;

import com.hw.oms.core.idFactory.SnowflakeId;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(Snowflake.class) //开启 @ConfigurationProperties
public class SnowflakeConfig {

    @Resource
    private Snowflake snowflake;

    @Bean
    public SnowflakeId snowflakeId(){
        return new SnowflakeId(snowflake);
    }
}
