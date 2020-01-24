package io.javac.minoss.minossstart.vertx;

import io.javac.minoss.minosscommon.annotation.RequestMapping;
import io.javac.minoss.minosscommon.config.MinOssProperties;
import io.javac.minoss.minosscommon.enums.RequestMethod;
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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Create by Pencilso on 2020/1/23 10:12 上午
 */
@Slf4j
@Component
public class VerticleMain extends AbstractVerticle {

    @Autowired
    private MinOssProperties minOssProperties;
    @Autowired
    private JwtPlugin jwtPlugin;
    @Autowired
    private ResourceLoader resourceLoader;

    /**
     * Controller 的包路径
     */
    private final String controllerBasePackage = "io.javac.minoss.minosscontroller";


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
        registerController(router);
        //设置静态资源
        router.route("/*").handler(StaticHandler.create());

        router.route().failureHandler(failhandler -> {
            failhandler.response().end("fail");
        });
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
    public Resource[] controllerResource() {
        ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        MetadataReaderFactory metaReader = new CachingMetadataReaderFactory(resourceLoader);

        String controllerBasePackagePath = controllerBasePackage.replace(".", "/");
        try {
            return resolver.getResources(String.format("classpath*:%s/**/*.class", controllerBasePackagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 注册
     */
    public void registerController(Router router) {
        try {
            Resource[] resources = controllerResource();
            for (Resource resource : resources) {
                String absolutePath = resource.getFile().getAbsolutePath().replace("/", ".");
                absolutePath = absolutePath.substring(absolutePath.indexOf(controllerBasePackage));
                absolutePath = absolutePath.replace(".class", "");
                if (StringUtils.isEmpty(absolutePath)) continue;
                //获取Class对象
                Class<?> controllerClass = Class.forName(absolutePath);
                //获取Controller的实例
                Object controller = MinossStartApplication.getContext().getBean(controllerClass);

                RequestMapping classAnnotation = controllerClass.getAnnotation(RequestMapping.class);
                //如果类 没有路径注解 则跳过
                if (classAnnotation == null) continue;
                //注册控制器里的方法
                registerControllerMethod(router, classAnnotation, controllerClass, controller);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void registerControllerMethod(Router router, RequestMapping classAnnotation, Class<?> controllerClass, Object controller) {
        //获取控制器里的方法
        Method[] controllerClassMethods = controllerClass.getMethods();
        for (Method controllerClassMethod : controllerClassMethods) {
            RequestMapping methodAnnotation = controllerClassMethod.getAnnotation(RequestMapping.class);
            //方法上没有注解的话 跳过执行
            if (methodAnnotation == null) continue;
            try {
                Handler<RoutingContext> methodHandler = (Handler<RoutingContext>) controllerClassMethod.invoke(controller);
                String url = classAnnotation.value()[0] + methodAnnotation.value()[0];
                RequestMethod method = methodAnnotation.method();

                log.info("register controller   class -> [{}]  method -> [{}]  url -> [{}]", controllerClass.getName(), controllerClassMethod.getName(), url);
                //路由
                Route route = null;
                switch (method) {
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

                if (methodAnnotation.blockingHandler()) {
                    route.blockingHandler(methodHandler);
                } else {
                    route.handler(methodHandler);
                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
