package com.join.event;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author z1aoyu 2024-11-13
 */
@Slf4j
@MapperScan(basePackages = {"com.join.event.mapper"})
@SpringBootApplication
public class JoinEventApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(JoinEventApplication.class);
        ConfigurableApplicationContext application = app.run(args);
        Environment env = application.getEnvironment();
        log.info("\n-------------------------服务启动成功---------------------------------\n\t" +
                        "服务的名称： '{}' is running! Access URLs:\n\t" +
                        "本地localhost: \t\thttp://localhost:{}\n\t" +
                        "本机服务ip: \thttp://{}:{}\n\t" +
                        "api文档api_doc: \thttp://{}:{}/doc.html\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port")
        );
    }

}
