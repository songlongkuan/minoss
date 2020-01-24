package io.javac.minoss.minossdao.service.impl;

import io.javac.minoss.minossdao.model.UserModel;
import io.javac.minoss.minossdao.mapper.UserMapper;
import io.javac.minoss.minossdao.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pencilso
 * @since 2020-01-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserModel> implements UserService {

}
