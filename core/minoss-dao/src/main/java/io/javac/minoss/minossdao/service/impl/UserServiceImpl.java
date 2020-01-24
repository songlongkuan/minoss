package io.javac.minoss.minossdao.service.impl;

import io.javac.minoss.minossdao.model.UserModel;
import io.javac.minoss.minossdao.mapper.UserMapper;
import io.javac.minoss.minossdao.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pencilso
 * @since 2020-01-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserModel> implements UserService {

    @Override
    public Optional<UserModel> getByLoginName(@NotBlank String loginName) {
        return Optional.ofNullable(lambdaQuery().eq(UserModel::getLoginName, loginName).one());
    }
}
