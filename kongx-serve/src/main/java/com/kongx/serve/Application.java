package com.kongx.serve;

import com.kongx.common.KongxBanner;
import com.kongx.serve.config.KongxConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication()
@EnableFeignClients(basePackages = "com.kongx")
@Configurable
@Import(KongxConfig.class)
@MapperScan("com.kongx.serve.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setBanner(new KongxBanner());
        application.run(args);
    }
}
