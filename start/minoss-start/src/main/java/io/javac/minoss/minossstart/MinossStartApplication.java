package io.javac.minoss.minossstart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javac.minoss.minosscommon.toolkit.Kv;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;

@EnableTransactionManagement
@MapperScan("io.javac.minoss.minossdao.mapper")
@ComponentScan("io.javac.minoss")
@Slf4j
@SpringBootApplication
@EnableCaching
public class MinossStartApplication {


    public static void main(String[] args) throws JsonProcessingException {

        SpringApplication.run(MinossStartApplication.class, args);
    }

}
