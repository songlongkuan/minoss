package io.javac.minoss.minosscommon.toolkit;

import org.springframework.context.ConfigurableApplicationContext;

/**
 * SpringBoot Context
 *
 * @author pencilso
 * @date 2020/1/25 10:59 上午
 */
public class SpringBootContext {
    private static ConfigurableApplicationContext context;

    public static ConfigurableApplicationContext getContext() {
        return context;
    }

    public static void setContext(ConfigurableApplicationContext context) {
        SpringBootContext.context = context;
    }
}
