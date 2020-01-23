package io.javac.minoss.minossstart;

import io.javac.minoss.minossstart.vertx.VerticleMain;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

@ComponentScan("io.javac.minoss")
@Slf4j
@SpringBootApplication
public class MinossStartApplication {
    private static ConfigurableApplicationContext context;


    public static void main(String[] args) {
        context = SpringApplication.run(MinossStartApplication.class, args);
    }


    public static ConfigurableApplicationContext getContext() {
        return context;
    }

    @EventListener
    public void deployVertx(ApplicationReadyEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
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
