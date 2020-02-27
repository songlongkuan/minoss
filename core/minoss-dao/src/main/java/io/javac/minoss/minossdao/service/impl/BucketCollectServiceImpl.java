package io.javac.minoss.minossdao.service.impl;

import io.javac.minoss.minossdao.model.BucketCollectModel;
import io.javac.minoss.minossdao.mapper.BucketCollectMapper;
import io.javac.minoss.minossdao.service.BucketCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * Bucket 汇总表 （各数据的统计） 服务实现类
 * </p>
 *
 * @author pencilso
 * @since 2020-01-25
 */
@Service
public class BucketCollectServiceImpl extends ServiceImpl<BucketCollectMapper, BucketCollectModel> implements BucketCollectService {

    @Override
    public List<BucketCollectModel> getByBucketMids(Collection<Long> bucketMids) {
        return lambdaQuery().in(BucketCollectModel::getBucketMid, bucketMids).list();
    }

    @Override
    public BucketCollectModel getByBucketMid(Long bucketMid) {
        return lambdaQuery().eq(BucketCollectModel::getBucketMid, bucketMid).one();
    }

    @Override
    public boolean incrFileSize(Long bucketMid, int incr) {
        return baseMapper.incrFileSize(bucketMid, incr);
    }

    @Override
    public boolean incrUsedSize(Long bucketMid, long length) {
        return baseMapper.incrUsedSize(bucketMid, length);
    }
}
