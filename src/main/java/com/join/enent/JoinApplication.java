package com.join.enent;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author z1aoyu 2025-03-11
 */
@Slf4j
@SpringCloudApplication
@ComponentScan(basePackages = {"com.join.enent"})
public class JoinApplication {
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(JoinApplication.class);
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
