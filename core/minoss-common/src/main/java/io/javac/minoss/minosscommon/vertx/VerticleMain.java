package io.javac.minoss.minosscommon.vertx;

import io.javac.minoss.minosscommon.annotation.RequestBlockingHandler;
import io.javac.minoss.minosscommon.annotation.RequestBody;
import io.javac.minoss.minosscommon.annotation.RequestInterceptClear;
import io.javac.minoss.minosscommon.annotation.RequestMapping;
import io.javac.minoss.minosscommon.base.BaseInterceptHandler;
import io.javac.minoss.minosscommon.config.MinOssProperties;
import io.javac.minoss.minosscommon.handler.GlobalExceptionHandler;
import io.javac.minoss.minosscommon.model.intercept.InterceptWrapper;
import io.javac.minoss.minosscommon.utils.SpringBootContext;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.IOException;
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
     * Controller 的包路径
     */
    private final String controllerBasePackage[] = {
            "io.javac.minoss.minossappadmin.controller",
            "io.javac.minoss.minossappclient.controller"
    };


    @Override
    public void start() throws Exception {
        super.start();
        //创建路由对象
        Router router = Router.router(vertx);
        //获取整个消息体 放进RoutingContext
        router.route().handler(BodyHandler.create());
        //创建http服务
        HttpServer server = vertx.createHttpServer();

        //开始注册路由
        for (String packagePath : controllerBasePackage) {
            registerController(router, packagePath);
        }

        //设置静态资源
        router.route("/*").handler(StaticHandler.create());
        //处理异常
        router.route().failureHandler(globalExceptionHandler);
        //启动监听
        server.requestHandler(router).listen(minOssProperties.getPort(), handler -> {
            log.info("vertx run prot : [{}] run state : [{}]", minOssProperties.getPort(), handler.succeeded());
        });

    }


    /**
     * 扫描控制器的类
     *
     * @return
     */
    public Resource[] controllerResource(String packagePath) {
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        String controllerBasePackagePath = packagePath.replace(".", "/");
        try {
            return resolver.getResources(String.format("classpath*:%s/**/*.class", controllerBasePackagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 注册Controller
     */
    private void registerController(@NotNull Router router, String packagePath) {
        if (SpringBootContext.getContext() == null) {
            log.warn("SpringBoot application context is null register controller is fail");
            return;
        }

        try {
            Resource[] resources = controllerResource(packagePath);
            for (Resource resource : resources) {
                String absolutePath = resource.getFile().getAbsolutePath().replace("/", ".");
                absolutePath = absolutePath.substring(absolutePath.indexOf(packagePath));
                absolutePath = absolutePath.replace(".class", "");
                if (StringUtils.isEmpty(absolutePath)) continue;
                //获取Class对象
                Class<?> controllerClass = Class.forName(absolutePath);
                //获取Controller的实例
                Object controller = SpringBootContext.getContext().getBean(controllerClass);

                RequestMapping classRequestMapping = controllerClass.getAnnotation(RequestMapping.class);
                //如果类 没有路径注解 则跳过
                if (classRequestMapping == null) continue;
                //注册控制器里的方法
                registerControllerMethod(router, classRequestMapping, controllerClass, controller);
            }
        } catch (Exception ex) {
            log.error("registerController fail ", ex);
        }
    }

    /**
     * 注册Controller的每个方法
     *
     * @param router              路由对象
     * @param classRequestMapping 类的api路径注解
     * @param controllerClass     类class
     * @param controller          Controller实例
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
                        //路由存在为空的情况 则跳过
                        if (StringUtils.isEmpty(superPath) || StringUtils.isEmpty(methodPath)) return;
                        String url = VerticleUtils.buildApiPath(superPath, methodPath);
                        //构建route
                        Route route = VerticleUtils.buildRouterUrl(url, router, methodRequestMapping.method());
                        //调用Controller里的方法 获取返回值 Handler
                        Handler<RoutingContext> methodHandler = (Handler<RoutingContext>) method.invoke(controller);
                        //开始注册拦截handler
                        addIntercept(url, route, controllerClass, method);
                        //开始注册Controller handler
                        RequestBlockingHandler requestBlockingHandler = Optional.ofNullable(method.getAnnotation(RequestBlockingHandler.class)).orElseGet(() -> controllerClass.getAnnotation(RequestBlockingHandler.class));
                        if (requestBlockingHandler != null) {
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
     * 添加拦截器
     *
     * @param url             api的url路径
     * @param route           路由
     * @param controllerClass 控制器Controller的类
     * @param method          Controller里的方法
     */
    public void addIntercept(@NotNull String url, @NotNull Route route, @NotNull Class<?> controllerClass, @NotNull Method method) {
        //检测是否跳过添加拦截器
        RequestInterceptClear requestInterceptClear = Optional.ofNullable(method.getAnnotation(RequestInterceptClear.class))
                .orElseGet(() -> controllerClass.getAnnotation(RequestInterceptClear.class));
        if (requestInterceptClear != null) {
            //跳过添加拦截器
            return;
        }
        List<InterceptWrapper.InterceptWrapperPojo> pojoList = interceptWrapper.getPojoList();
        pojoList.forEach(it -> {
            try {
                if (!url.startsWith(it.getApiPath())) {
                    return;
                }
                BaseInterceptHandler interceptHandler = SpringBootContext.getContext().getBean(it.getInterceptHandler());
                route.handler(interceptHandler);
            } catch (Exception ex) {
                log.warn("interceptHandler addIntercept fail class by [{}]", it.getInterceptHandler());
            }
        });
    }
}
