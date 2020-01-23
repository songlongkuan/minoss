package io.javac.minoss.minossstart;

import io.javac.minoss.minossstart.vertx.VerticleMain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author pencilso
 * @date 2020/1/23 10:18 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VertxTests {

    @Test
    public void findClass() {
        VerticleMain verticleMain = new VerticleMain();
        Resource[] resources = verticleMain.controllerResource();
    }
}
