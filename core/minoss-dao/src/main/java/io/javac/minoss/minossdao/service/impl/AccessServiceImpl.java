package io.javac.minoss.minossdao.service.impl;

import io.javac.minoss.minossdao.model.AccessModel;
import io.javac.minoss.minossdao.mapper.AccessMapper;
import io.javac.minoss.minossdao.service.AccessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * bucket 授权 服务实现类
 * </p>
 *
 * @author pencilso
 * @since 2020-02-06
 */
@Service
public class AccessServiceImpl extends ServiceImpl<AccessMapper, AccessModel> implements AccessService {

}
