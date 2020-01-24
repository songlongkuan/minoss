package io.javac.minoss.minossstart;

import io.javac.minoss.minosscommon.bcrypt.BCryptPasswordEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author pencilso
 * @date 2020/1/24 12:10 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MinOssDaoTests {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Test
    public void insertUser() {
        String encode = bCryptPasswordEncoder.encode("1237654321");
        System.out.println("pwd:"+encode);
    }
}
