package io.javac.minoss.minossdao.service.impl;

import io.javac.minoss.minossdao.model.AccessBucketModel;
import io.javac.minoss.minossdao.mapper.AccessBucketMapper;
import io.javac.minoss.minossdao.service.AccessBucketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pencilso
 * @since 2020-02-06
 */
@Service
public class AccessBucketServiceImpl extends ServiceImpl<AccessBucketMapper, AccessBucketModel> implements AccessBucketService {

    @Override
    public AccessBucketModel getByAccessMidAndBucketMid(@NotNull Long accessMid, @NotNull Long bucketMid) {
        return lambdaQuery().eq(AccessBucketModel::getAccessMid, accessMid).eq(AccessBucketModel::getBucketMid, bucketMid).one();
    }

    @Override
    public List<AccessBucketModel> getByAccessMid(@NotNull Long accessMid) {
        return lambdaQuery().eq(AccessBucketModel::getAccessMid, accessMid).list();
    }
}
