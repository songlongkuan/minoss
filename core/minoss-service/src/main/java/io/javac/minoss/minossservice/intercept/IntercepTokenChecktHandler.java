package io.javac.minoss.minossservice.intercept;

import io.javac.minoss.minossservice.base.BaseInterceptHandler;
import io.javac.minoss.minosscommon.cache.StringCacheStore;
import io.javac.minoss.minosscommon.constant.CacheConst;
import io.javac.minoss.minosscommon.exception.MinOssTokenExpireException;
import io.javac.minoss.minosscommon.exception.MinOssTokenInvalidException;
import io.javac.minoss.minosscommon.model.jwt.JwtAuthModel;
import io.javac.minoss.minosscommon.plugin.JwtPlugin;
import io.javac.minoss.minossservice.vertx.VertxRequest;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * check  api token right
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
    public void handle(String accesstoken, RoutingContext routingContext, VertxRequest vertxRequest) {
        JwtAuthModel jwtAuthModel = jwtPlugin.getOauthEntity(accesstoken).orElseThrow(MinOssTokenInvalidException::new);
        //检测jwt salt是否与缓存一致 （单点登录）
        String jwtSalt = stringCacheStore.get(CacheConst.CACHE_OBJECT_JWT_SALT + jwtAuthModel.getId()).orElseThrow(MinOssTokenExpireException::new);
        if (!jwtAuthModel.getSalt().equals(jwtSalt)) {
            throw new MinOssTokenExpireException();
        }
        routingContext.put("jwt_auth", jwtAuthModel);
        routingContext.put("vertx_request", vertxRequest);
        routingContext.next();
    }
}
