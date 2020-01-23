package io.javac.minoss.minossstart.configuration;

import io.javac.minoss.minosscacheimpl.LevelCacheStore;
import io.javac.minoss.minosscommon.cache.StringCacheStore;
import io.javac.minoss.minosscommon.config.MinOssProperties;
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

    @Bean
    public MinOssProperties minOssProperties() {
        return new MinOssProperties();
    }

    @Bean
    public JwtPlugin jwtPlugin() {
        return new JwtPlugin();
    }

    @Bean
    public StringCacheStore stringCacheStore() {
        return new LevelCacheStore();
    }

}
