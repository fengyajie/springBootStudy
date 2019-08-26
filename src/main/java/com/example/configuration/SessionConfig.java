package com.example.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * maxInactiveIntervalInSeconds: 设置 Session 失效时间，使用 Redis Session 之后，原 Spring Boot 的 server.session.timeout 属性不再生效
 * @author fyj
 *
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=3600*24)
public class SessionConfig {
	@Bean
    public static ConfigureRedisAction configureRedisAction()
    {
        //让springSession不再执行config命令
        return ConfigureRedisAction.NO_OP;
    }

}
