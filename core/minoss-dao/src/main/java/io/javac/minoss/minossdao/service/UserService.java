package io.javac.minoss.minossdao.service;

import io.javac.minoss.minossdao.model.UserModel;
import io.javac.minoss.minossdao.base.BaseIService;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author pencilso
 * @since 2020-01-24
 */
public interface UserService extends BaseIService<UserModel> {

    /**
     * 根据登录名 查询用户
     *
     * @param loginName
     * @return
     */
    Optional<UserModel> getByLoginName(@NotBlank String loginName);
}
