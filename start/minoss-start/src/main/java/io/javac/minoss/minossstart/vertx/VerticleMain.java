package io.javac.minoss.minossstart.vertx;

import io.javac.minoss.minosscommon.annotation.RequestBlockingHandler;
import io.javac.minoss.minosscommon.annotation.RequestMapping;
import io.javac.minoss.minosscommon.config.MinOssProperties;
import io.javac.minoss.minosscommon.enums.RequestMethod;
import io.javac.minoss.minosscommon.handler.GlobalExceptionHandler;
import io.javac.minoss.minosscommon.plugin.JwtPlugin;
import io.javac.minoss.minossstart.MinossStartApplication;
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
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

    /**
     * Controller 的包路径
     */
    private final String controllerBasePackage[] = {"io.javac.minoss.minossappadmin.controller", "io.javac.minoss.minossappclient.controller"};


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
    public void registerController(@NotNull Router router, String packagePath) {
        if (MinossStartApplication.getContext() == null) {
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
                Object controller = MinossStartApplication.getContext().getBean(controllerClass);

                RequestMapping classRequestMapping = controllerClass.getAnnotation(RequestMapping.class);
                //如果类 没有路径注解 则跳过
                if (classRequestMapping == null) continue;
                //注册控制器里的方法
                registerControllerMethod(router, classRequestMapping, controllerClass, controller);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
    public void registerControllerMethod(@NotNull Router router, @NotNull RequestMapping classRequestMapping, @NotNull Class<?> controllerClass, @NotNull Object controller) {
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
                        if (!superPath.endsWith("/")) {
                            superPath += "/";
                        }
                        if (methodPath.startsWith("/")) {
                            methodPath = methodPath.substring(1);
                        }
                        String url = superPath + methodPath;
                        //路由
                        Route route;
                        switch (methodRequestMapping.method()) {
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
                        //调用Controller里的方法 获取返回值 Handler
                        Handler<RoutingContext> methodHandler = (Handler<RoutingContext>) method.invoke(controller);
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
}
