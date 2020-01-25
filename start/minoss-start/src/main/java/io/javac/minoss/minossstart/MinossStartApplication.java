package io.javac.minoss.minossstart;

import io.javac.minoss.minosscommon.utils.SpringBootContext;
import io.javac.minoss.minosscommon.vertx.VerticleMain;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

@MapperScan("io.javac.minoss.minossdao.mapper")
@ComponentScan("io.javac.minoss")
@Slf4j
@SpringBootApplication
public class MinossStartApplication {


    public static void main(String[] args) {
        SpringApplication.run(MinossStartApplication.class, args);
    }


    @EventListener
    public void deployVertx(ApplicationReadyEvent event) {
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
