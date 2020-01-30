package io.javac.minoss.minoss.minossappservice.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.javac.minoss.minoss.minossappservice.VertxBucketService;
import io.javac.minoss.minosscommon.exception.MinOssMessageException;
import io.javac.minoss.minosscommon.model.param.ParamInsertBucketBO;
import io.javac.minoss.minosscommon.model.param.ParamUpdateBucketBO;
import io.javac.minoss.minosscommon.model.vo.BucketVO;
import io.javac.minoss.minosscommon.utils.id.IdGeneratorCore;
import io.javac.minoss.minossdao.model.BucketCollectModel;
import io.javac.minoss.minossdao.model.BucketModel;
import io.javac.minoss.minossdao.service.BucketCollectService;
import io.javac.minoss.minossdao.service.BucketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author pencilso
 * @date 2020/1/25 10:17 下午
 */
@Slf4j
@Validated
@Service
public class VertxBucketServiceImpl implements VertxBucketService {

    @Autowired
    private BucketService bucketService;
    @Autowired
    private BucketCollectService bucketCollectService;


    @Override
    public List<BucketVO> listPage(Page page) {
        //查询bucket空间列表
        Page<BucketModel> bucketModelPage = bucketService.page(page);
        List<BucketModel> bucketModelList = bucketModelPage.getRecords();
        List<Long> bucketMids = bucketModelList.stream().map(BucketModel::getMid).collect(Collectors.toList());
        //查询对应的统计数据
        Map<Long, BucketCollectModel> bucketCollectModelMap = (bucketMids.isEmpty() ? new ArrayList<BucketCollectModel>() : bucketCollectService.getByBucketMids(bucketMids))
                .stream().collect(Collectors.toMap(BucketCollectModel::getBucketMid, bucket -> bucket));
        //组装要响应的数据
        List<BucketVO> responeBucketVO = new ArrayList<>(bucketModelList.size());
        bucketModelList.forEach(it -> {
            Optional.ofNullable(bucketCollectModelMap.get(it.getMid())).ifPresent(bucketCollectModel -> {
                BucketVO bucketVO = new BucketVO();
                BeanUtils.copyProperties(bucketCollectModel, bucketVO);
                BeanUtils.copyProperties(it, bucketVO);
                responeBucketVO.add(bucketVO);
            });
        });
        return responeBucketVO;
    }

    @Transactional
    @Override
    public boolean insert(@Valid ParamInsertBucketBO paramInsertBucketBO) {
        BucketModel bucketModel = new BucketModel();
        BeanUtils.copyProperties(paramInsertBucketBO, bucketModel);
        bucketModel.setMid(IdGeneratorCore.generatorId());
        boolean insertBucket = bucketService.save(bucketModel);
        if (!insertBucket) {
            log.warn("insert new bucket fail param: [{}]", paramInsertBucketBO);
            throw new MinOssMessageException("新增Bucket出错，请尝试稍后重试！");
        }

        //插入统计汇总 表
        BucketCollectModel bucketCollectModel = new BucketCollectModel();
        bucketCollectModel.setBucketMid(bucketModel.getMid())
                .setStoreFileSize(0L)
                .setStoreUsedSize(0L)
                .setMid(IdGeneratorCore.generatorId());
        boolean insertBucketCollect = bucketCollectService.save(bucketCollectModel);
        if (!insertBucketCollect) {
            log.warn("insert new bucket collect fail param: [{}]", paramInsertBucketBO);
            throw new MinOssMessageException("新增Bucket汇总数据出错，请尝试稍后重试！");
        }
        return true;
    }

    @Override
    public boolean update(Integer version, @Valid ParamUpdateBucketBO paramUpdateBucketBO) {
        BucketModel bucketModel = new BucketModel();
        BeanUtils.copyProperties(paramUpdateBucketBO, bucketModel);
        bucketModel.setVersion(version);
        boolean updateBucket = bucketService.updateModelByMid(bucketModel, paramUpdateBucketBO.getMid());
        log.debug("update bucket state : [{}]  version: [{}] paramUpdateBucketBO : [{}]", updateBucket, version, paramUpdateBucketBO);
        return updateBucket;
    }


    @Override
    public BucketModel getBucketModel(@NotNull Long mid) {
        return bucketService.getByMid(mid);
    }
}
