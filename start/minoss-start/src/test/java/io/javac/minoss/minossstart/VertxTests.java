package io.javac.minoss.minossstart;

import io.javac.minoss.minossbridge.cache.LevelCacheStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author pencilso
 * @date 2020/1/23 10:18 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class VertxTests {
    @Autowired
    LevelCacheStore levelCacheStore;
    @Test
    public void findClass() {

    }
}
