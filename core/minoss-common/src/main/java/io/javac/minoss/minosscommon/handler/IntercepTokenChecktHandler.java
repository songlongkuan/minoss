package io.javac.minoss.minosscommon.handler;

import io.javac.minoss.minosscommon.base.BaseInterceptHandler;
import io.javac.minoss.minosscommon.cache.StringCacheStore;
import io.javac.minoss.minosscommon.constant.CacheConst;
import io.javac.minoss.minosscommon.exception.MinOssTokenExpireException;
import io.javac.minoss.minosscommon.exception.MinOssTokenInvalidException;
import io.javac.minoss.minosscommon.model.jwt.JwtAuthEntity;
import io.javac.minoss.minosscommon.plugin.JwtPlugin;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 拦截器 检验token是否有效
 *
 * @author pencilso
 * @date 2020/1/25 11:15 上午
 */
@Component
public class IntercepTokenChecktHandler extends BaseInterceptHandler {
    @Autowired
    private JwtPlugin jwtPlugin;
    @Autowired
    private StringCacheStore stringCacheStore;

    @Override
    public void handle(String accesstoken, RoutingContext routingContext) {
        JwtAuthEntity jwtAuthEntity = jwtPlugin.getOauthEntity(accesstoken).orElseThrow(MinOssTokenInvalidException::new);
        //检测jwt salt是否与缓存一致 （单点登录）
        String jwtSalt = stringCacheStore.get(CacheConst.CACHE_USER_JWT_SALT + jwtAuthEntity.getUMid()).orElseThrow(MinOssTokenExpireException::new);
        if (!jwtAuthEntity.getSalt().equals(jwtSalt)) {
            throw new MinOssTokenExpireException();
        }
        routingContext.put("jwt_auth", jwtAuthEntity);
        routingContext.next();
    }
}