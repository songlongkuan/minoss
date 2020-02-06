package io.javac.minoss.minossstart.configuration;

import io.javac.minoss.minossbridge.bcrypt.BCryptPasswordEncoder;
import io.javac.minoss.minossbridge.cache.LevelCacheStore;
import io.javac.minoss.minosscommon.bcrypt.PasswordEncoder;
import io.javac.minoss.minosscommon.cache.StringCacheStore;
import io.javac.minoss.minosscommon.config.MinOssProperties;
import io.javac.minoss.minosscommon.handler.IntercepTokenChecktHandler;
import io.javac.minoss.minosscommon.model.intercept.InterceptWrapper;
import io.javac.minoss.minosscommon.plugin.JwtPlugin;
import io.vertx.core.Vertx;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create by Pencilso on 2020/1/23 9:51 上午
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }

    /**
     * 项目变量
     *
     * @return
     */
    @Bean
    public MinOssProperties minOssProperties() {
        return new MinOssProperties();
    }

    /**
     * jwt加密
     *
     * @return
     */
    @Bean
    public JwtPlugin jwtPlugin() {
        return new JwtPlugin();
    }

    /**
     * 缓存实现
     *
     * @return
     */
    @Bean
    public StringCacheStore stringCacheStore() {
        return new LevelCacheStore();
    }

    /**
     * 密码加密
     *
     * @return
     */
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置拦截器
     *
     * @return
     */
    @Bean
    public InterceptWrapper interceptWrapper() {
        InterceptWrapper interceptWrapper = new InterceptWrapper();
        interceptWrapper
                .addIntercept(new InterceptWrapper.InterceptWrapperPojo().setApiPath("/api/admin/").setInterceptHandler(IntercepTokenChecktHandler.class))
        ;
        return interceptWrapper;
    }
}
