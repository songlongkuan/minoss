package io.javac.minoss.minossstart;

import io.javac.minoss.minosscommon.utils.id.IdGeneratorCore;
import io.javac.minoss.minossdao.model.UserModel;
import io.javac.minoss.minossdao.service.UserService;
import io.javac.minoss.minossstart.vertx.VerticleMain;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

/**
 * @author pencilso
 * @date 2020/1/24 12:10 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MinOssDaoTests {
    @Autowired
    private UserService userService;

    @Test
    public void insertUser() {
        UserModel userModel = new UserModel();
        userModel.setJwtToken(UUID.randomUUID().toString());
        userModel.setMid(IdGeneratorCore.generatorId());
        userModel.setLoginName("pencilso");
        userModel.setLoginPassword(IdGeneratorCore.generatorUUID());
        userModel.setJwtSalt(IdGeneratorCore.generatorUUID());
        boolean save = userService.save(userModel);
        System.out.println("saveUserL" + save);
    }

    @Test
    public void findUserList() {
        List<UserModel> list = userService.list();
        System.out.println("userList:" + list);
        System.out.println("userListSIze:" + list.size());
    }
}
