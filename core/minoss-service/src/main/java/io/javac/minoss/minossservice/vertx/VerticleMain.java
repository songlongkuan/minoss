package io.javac.minoss.minossservice.vertx;

import io.javac.minoss.minosscommon.annotation.RequestBlockingHandler;
import io.javac.minoss.minosscommon.annotation.RequestBody;
import io.javac.minoss.minosscommon.annotation.RequestInterceptClear;
import io.javac.minoss.minosscommon.annotation.RequestMapping;
import io.javac.minoss.minossservice.base.BaseInterceptHandler;
import io.javac.minoss.minosscommon.config.MinOssProperties;
import io.javac.minoss.minossservice.intercept.InterceptWrapper;
import io.javac.minoss.minosscommon.toolkit.SpringBootContext;
import io.javac.minoss.minossservice.handler.GlobalExceptionHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.ext.web.handler.LoggerHandler;
import io.vertx.ext.web.handler.StaticHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Create by Pencilso on 2020/1/23 10:12 上午
 */
@Validated
@Slf4j
@Component
public class VerticleMain extends AbstractVerticle {

    @Autowired
    private MinOssProperties minOssProperties;
    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;
    @Autowired
    private InterceptWrapper interceptWrapper;

    /**
     * controller package
     */
    private final String controllerBasePackage[] = {
            "io.javac.minoss.minossappadmin",
            "io.javac.minoss.minossappclient"
    };

    /**
     * vertx deploy start
     *
     * @throws Exception
     */
    @Override
    public void start() throws Exception {
        super.start();
        Router router = Router.router(vertx);
        if (minOssProperties.isDevlog()) {
            router.route("/api/*").handler(LoggerHandler.create(LoggerFormat.SHORT));
        }
        //register controller
        for (String packagePath : controllerBasePackage) {
            registerController(router, packagePath);
        }
        router.route("/*").handler(StaticHandler.create());
        //register all exception global handler
        router.route().failureHandler(globalExceptionHandler);

        //start listen port
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(router).listen(minOssProperties.getPort(), handler -> {
            log.info("vertx run prot : [{}] run state : [{}]", minOssProperties.getPort(), handler.succeeded());
        });

    }


    /**
     * register controller
     */
    private void registerController(@NotNull Router router, String packagePath) {
        if (SpringBootContext.getContext() == null) {
            log.warn("SpringBoot application context is null register controller is fail");
            return;
        }

        try {
            Resource[] resources = VerticleUtils.scannerControllerClass(packagePath, resourceLoader);
            for (Resource resource : resources) {
                String absolutePath = resource.getFile().getAbsolutePath().replace(File.separator, ".");
                absolutePath = absolutePath.substring(absolutePath.indexOf(packagePath));
                absolutePath = absolutePath.replace(".class", "");
                if (StringUtils.isEmpty(absolutePath)) continue;
                //get class
                Class<?> controllerClass = Class.forName(absolutePath);
                //from class get controller instance bean
                Object controller = SpringBootContext.getContext().getBean(controllerClass);

                RequestMapping classRequestMapping = controllerClass.getAnnotation(RequestMapping.class);
                //if controller class not have requestMapping annotation -> skip register
                if (classRequestMapping == null) continue;
                //register controller method
                registerControllerMethod(router, classRequestMapping, controllerClass, controller);
            }
        } catch (Exception ex) {
            log.error("registerController fail ", ex);
        }
    }


    /**
     * register controller method
     *
     * @param router              route
     * @param classRequestMapping controller requestMapping annotation
     * @param controllerClass     controller class
     * @param controller          controller instance
     */
    private void registerControllerMethod(@NotNull Router router, @NotNull RequestMapping classRequestMapping, @NotNull Class<?> controllerClass, @NotNull Object controller) {
        //获取控制器里的方法
        Method[] controllerClassMethods = controllerClass.getMethods();
        Arrays.asList(controllerClassMethods).stream()
                .filter(method -> method.getAnnotation(RequestMapping.class) != null)
                .forEach(method -> {
                    try {
                        RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
                        String superPath = classRequestMapping.value()[0];
                        String methodPath = methodRequestMapping.value()[0];
                        //if api path empty skip
                        if (StringUtils.isEmpty(superPath) || StringUtils.isEmpty(methodPath)) return;
                        String url = VerticleUtils.buildApiPath(superPath, methodPath);
                        //build route
                        Route route = VerticleUtils.buildRouterUrl(url, router, methodRequestMapping.method());
                        //run controller method get Handler object
                        Handler<RoutingContext> methodHandler = (Handler<RoutingContext>) method.invoke(controller);
                        //add intercept
                        addIntercept(url, route, controllerClass, method);
                        //register controller mthod Handler object
                        RequestBlockingHandler requestBlockingHandler = Optional.ofNullable(method.getAnnotation(RequestBlockingHandler.class)).orElseGet(() -> controllerClass.getAnnotation(RequestBlockingHandler.class));
                        if (requestBlockingHandler != null) {
                            //register blocking handler
                            route.blockingHandler(methodHandler);
                        } else {
                            route.handler(methodHandler);
                        }

                        log.info("register controller -> [{}]  method -> [{}]  url -> [{}] ", controllerClass.getName(), method.getName(), url);
                    } catch (Exception e) {
                        log.error("registerControllerMethod fail controller: [{}]  method: [{}]", controllerClass, method.getName());
                    }
                });

    }

    /**
     * add intercept handler
     *
     * @param url             api path
     * @param route           route
     * @param controllerClass controller class
     * @param method          controller method
     */
    public void addIntercept(@NotNull String url, @NotNull Route route, @NotNull Class<?> controllerClass, @NotNull Method method) {
        //register bodyAsJson handler
        Optional.ofNullable(method.getAnnotation(RequestBody.class)).ifPresent(requestBody -> {
            route.handler(BodyHandler.create());
        });

        //if skip add intercept handler
        RequestInterceptClear requestInterceptClear = Optional.ofNullable(method.getAnnotation(RequestInterceptClear.class))
                .orElseGet(() -> controllerClass.getAnnotation(RequestInterceptClear.class));
        if (requestInterceptClear != null) {
            //skip
            return;
        }
        List<InterceptWrapper.InterceptWrapperPojo> pojoList = interceptWrapper.getPojoList();
        pojoList.forEach(it -> {
            try {
                //matching api path
                if (!url.startsWith(it.getApiPath())) {
                    return;
                }
                //register intercept handler
                BaseInterceptHandler interceptHandler = SpringBootContext.getContext().getBean(it.getInterceptHandler());
                route.handler(interceptHandler);
            } catch (Exception ex) {
                log.warn("interceptHandler addIntercept fail class by [{}]", it.getInterceptHandler());
            }
        });
    }
}
