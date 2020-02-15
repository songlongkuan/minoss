package io.javac.minoss.minossservice.listener;

import io.javac.minoss.minosscommon.toolkit.SpringBootContext;
import io.javac.minoss.minossservice.vertx.VerticleMain;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Spring Boot Ready Listener
 * Deploy Vertx
 * @author pencilso
 * @date 2020/2/15 2:06 下午
 */
@Slf4j
@Component
public class DeployVertxListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        //SpringBoot 加载完毕
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        SpringBootContext.setContext(applicationContext);
        //deploy vert.x
        Vertx vertx = applicationContext.getBean(Vertx.class);
        VerticleMain verticleMain = applicationContext.getBean(VerticleMain.class);
        vertx.deployVerticle(verticleMain, handler -> {
            log.info("vertx deploy state [{}]", handler.succeeded());
            if (handler.failed()) {
                log.error("verx deploy failed message: [{}]", handler.result());
            }
        });
    }
}
