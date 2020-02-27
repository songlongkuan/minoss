package io.javac.minoss.minossdao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.javac.minoss.minossdao.mapper.BucketMapper;
import io.javac.minoss.minossdao.model.BucketModel;
import io.javac.minoss.minossdao.service.BucketService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * bucket 存储空间 服务实现类
 * </p>
 *
 * @author pencilso
 * @since 2020-01-25
 */
@Service
public class BucketServiceImpl extends ServiceImpl<BucketMapper, BucketModel> implements BucketService {


    @Override
    public BucketModel getByBucketName(@NotBlank String bucketName) {
        return lambdaQuery().eq(BucketModel::getBucketName,bucketName).one();
    }
}
