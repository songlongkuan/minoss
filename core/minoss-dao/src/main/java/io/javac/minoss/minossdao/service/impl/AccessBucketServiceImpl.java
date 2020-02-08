package io.javac.minoss.minossdao.service.impl;

import io.javac.minoss.minossdao.model.AccessBucketModel;
import io.javac.minoss.minossdao.mapper.AccessBucketMapper;
import io.javac.minoss.minossdao.service.AccessBucketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pencilso
 * @since 2020-02-06
 */
@Service
public class AccessBucketServiceImpl extends ServiceImpl<AccessBucketMapper, AccessBucketModel> implements AccessBucketService {

    @Override
    public AccessBucketModel getByAccessMidAndBucketMid(Long accessMid, Long bucketMid) {
        return lambdaQuery().eq(AccessBucketModel::getAccessMid, accessMid).eq(AccessBucketModel::getBucketMid, bucketMid).one();
    }
}
