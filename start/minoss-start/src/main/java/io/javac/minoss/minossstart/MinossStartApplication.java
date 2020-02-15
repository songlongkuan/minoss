package io.javac.minoss.minossstart;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan("io.javac.minoss.minossdao.mapper")
@ComponentScan("io.javac.minoss")
@Slf4j
@SpringBootApplication
public class MinossStartApplication {


    public static void main(String[] args) {
        SpringApplication.run(MinossStartApplication.class, args);
    }

}
