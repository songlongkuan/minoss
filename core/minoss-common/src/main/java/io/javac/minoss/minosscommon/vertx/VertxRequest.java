package io.javac.minoss.minosscommon.vertx;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.javac.minoss.minosscommon.config.MinOssProperties;
import io.javac.minoss.minosscommon.exception.MinOssMessageException;
import io.javac.minoss.minosscommon.model.jwt.JwtAuthModel;
import io.javac.minoss.minosscommon.toolkit.Assert;
import io.javac.minoss.minosscommon.toolkit.JsonUtils;
import io.javac.minoss.minosscommon.toolkit.SpringBootContext;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ValidationException;
import java.io.IOException;
import java.util.Optional;

/**
 * @author pencilso
 * @date 2020/1/25 12:20 下午
 */
@Slf4j
public class VertxRequest {
    private final RoutingContext routingContext;

    private VertxRequest(RoutingContext routingContext) {
        this.routingContext = routingContext;
        MinOssProperties minOssProperties = SpringBootContext.getBean(MinOssProperties.class);
        if (!minOssProperties.isDevlog()) return;

        // out logs
        HttpServerRequest request = routingContext.request();
        String uri = request.uri();
        HttpMethod method = request.method();

        JsonObject bodyAsJson = routingContext.getBodyAsJson();
        log.info("request uri: [{}] method: [{}] body as json: [{}]", uri, method, bodyAsJson);


    }

    public static VertxRequest build(RoutingContext routingContext) {
        return new VertxRequest(routingContext);
    }


    /**
     * 获取token解析后的用户id
     *
     * @return
     */
    public JwtAuthModel getAuthEntitu() {
        return routingContext.get("jwt_auth");
    }

    /**
     * 构建 VertxRespone
     *
     * @return VertxRespone
     */
    public VertxRespone buildVertxRespone() {
        return VertxRespone.build(routingContext);
    }

    /**
     * 获取AccessToken 令牌
     *
     * @return
     */
    public Optional<String> getAccessToken() {
        HttpServerRequest request = routingContext.request();
        String accesstoken = null;
        if ((accesstoken = request.getHeader("accesstoken")) != null) {
        } else if ((accesstoken = request.getParam("accesstoken")) != null) {
        } else {
            Cookie cookie = request.getCookie("accesstoken");
            if (cookie != null) accesstoken = cookie.getValue();
        }
        return Optional.ofNullable(accesstoken);
    }

    /**
     * 获取分页参数
     *
     * @return
     */
    public Page buildPage() {
        HttpServerRequest request = routingContext.request();
        Integer pageNum = getParamToInt("pageNum").orElse(1);
        Integer pageSize = getParamToInt("pageSize").orElse(20);
        return new Page(pageNum, pageSize);
    }


    /**
     * 获取参数 -String
     *
     * @param key
     * @return
     */
    public Optional<String> getParam(String key) {
        HttpServerRequest request = routingContext.request();
        return Optional.ofNullable(request.getParam(key));
    }

    /**
     * 获取参数 -Integer
     *
     * @param key
     * @return
     */
    public Optional<Integer> getParamToInt(String key) {
        Integer value = null;
        Optional<String> param = getParam(key);
        if (param.isPresent()) {
            value = Integer.valueOf(param.get());
        }
        return Optional.ofNullable(value);
    }

    /**
     * 获取参数 -Byte
     *
     * @param key
     * @return
     */
    public Optional<Byte> getParamToByte(String key) {
        Byte value = null;
        Optional<String> param = getParam(key);
        if (param.isPresent()) {
            value = Byte.valueOf(param.get());
        }
        return Optional.ofNullable(value);
    }

    /**
     * 获取参数 -Boolean
     *
     * @param key
     * @return
     */
    public Optional<Boolean> getParamToBoolean(String key) {
        Boolean value = null;
        Optional<String> param = getParam(key);
        if (param.isPresent()) {
            value = Boolean.valueOf(param.get());
        }
        return Optional.ofNullable(value);
    }

    /**
     * 获取参数 - JavaBean
     *
     * @param <T>
     * @param paramClass
     * @return
     */
    public <T> T getBodyJsonToBean(Class<?> paramClass) {
        String bodyAsString = routingContext.getBodyAsString();
        Assert.notBlank(bodyAsString, "body json can not be null");
        T param = null;
        try {
            param = (T) JsonUtils.jsonToObject(bodyAsString, paramClass);
        } catch (IOException e) {
            log.warn("getParamBean json to object fial body as string: [{}]", bodyAsString, e);
            throw new ValidationException("json to object param fail");
        }
        return param;
    }

    /**
     * 获取参数 并且不能为空
     *
     * @param key
     * @return
     */
    public String getParamNotBlank(String key) {
        return getParam(key).orElseThrow(() -> new MinOssMessageException(key + " can not be null"));
    }
}
