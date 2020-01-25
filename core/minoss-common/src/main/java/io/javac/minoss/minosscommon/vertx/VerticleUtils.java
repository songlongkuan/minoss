package io.javac.minoss.minosscommon.vertx;

import io.javac.minoss.minosscommon.enums.RequestMethod;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author pencilso
 * @date 2020/1/25 10:47 上午
 */
@Validated
public class VerticleUtils {


    public static String buildApiPath(@NotNull String superPath, @NotNull String methodPath) {
        if (!superPath.endsWith("/")) {
            superPath += "/";
        }
        if (methodPath.startsWith("/")) {
            methodPath = methodPath.substring(1);
        }
        return superPath + methodPath;
    }

    public static Route buildRouterUrl(String url, Router router, RequestMethod requestMethod) {
        //路由
        Route route;
        switch (requestMethod) {
            case POST:
                route = router.post(url);
                break;
            case PUT:
                route = router.put(url);
                break;
            case DELETE:
                route = router.delete(url);
                break;
            case ROUTE:
                route = router.route(url);
                break;
            case GET: // fall through
            default:
                route = router.get(url);
                break;
        }
        return route;
    }
}
