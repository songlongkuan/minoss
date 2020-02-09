package io.javac.minoss.minoss.minossappservice.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.javac.minoss.minoss.minossappservice.VertxAccessService;
import io.javac.minoss.minosscommon.bcrypt.PasswordEncoder;
import io.javac.minoss.minosscommon.exception.MinOssMessageException;
import io.javac.minoss.minosscommon.model.param.ParamInsertAccessBO;
import io.javac.minoss.minosscommon.model.param.ParamUpdateAccessBO;
import io.javac.minoss.minosscommon.model.param.ParamUpdateAccessBucketBO;
import io.javac.minoss.minosscommon.model.vo.AccessVO;
import io.javac.minoss.minosscommon.toolkit.id.IdGeneratorCore;
import io.javac.minoss.minossdao.model.AccessBucketModel;
import io.javac.minoss.minossdao.model.AccessModel;
import io.javac.minoss.minossdao.service.AccessBucketService;
import io.javac.minoss.minossdao.service.AccessService;
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
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * controller access service
 *
 * @author pencilso
 * @date 2020/2/6 8:28 下午
 */
@Validated
@Slf4j
@Service
public class VertxAccessServiceImpl implements VertxAccessService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccessService accessService;
    @Autowired
    private AccessBucketService accessBucketService;


    @Override
    public Page<AccessVO> listPageVO(Page page) {
        Page<AccessModel> queryPage = accessService.page(page);
        Page<AccessVO> responePage = new Page<>();
        BeanUtils.copyProperties(queryPage, responePage);
        //init list
        responePage.setRecords(new ArrayList<>(queryPage.getRecords().size()));
        queryPage.getRecords().forEach(it -> {
            responePage.getRecords().add(dto2vo(it));
        });
        //respone
        return responePage;
    }

    @Override
    public @NotNull AccessVO dto2vo(@NotNull AccessModel accessModel) {
        AccessVO accessVO = new AccessVO();
        BeanUtils.copyProperties(accessModel, accessVO);
        return accessVO;
    }

    @Override
    public boolean insert(@Valid ParamInsertAccessBO paramInsertAccessBO) {
        AccessModel accessModel = new AccessModel();
        BeanUtils.copyProperties(paramInsertAccessBO, accessModel);

        String accesskey = IdGeneratorCore.generatorUUID();
        String accessSecret = passwordEncoder.encode(accesskey);

        //set mid
        accessModel.setMid(IdGeneratorCore.generatorId());
        accessModel.setAccessKey(accesskey);
        accessModel.setAccessSecret(accessSecret);
        //save db
        return accessService.save(accessModel);
    }

    @Override
    public Optional<AccessVO> getAccessVOModel(@NotNull Long accessMid) {
        AccessVO accessVO = null;
        //query db
        Optional<AccessModel> accessModel = getAccessModel(accessMid);
        if (accessModel.isPresent()) {
            accessVO = new AccessVO();
            BeanUtils.copyProperties(accessModel.get(), accessVO);
        }
        return Optional.ofNullable(accessVO);
    }

    @Override
    public Optional<AccessModel> getAccessModel(@NotNull Long accessMid) {
        return Optional.ofNullable(accessService.getByMid(accessMid));
    }

    @Override
    public boolean update(@NotNull Integer version, @Valid ParamUpdateAccessBO paramUpdateAccessBO) {
        AccessModel accessModel = new AccessModel();
        BeanUtils.copyProperties(paramUpdateAccessBO, accessModel);
        accessModel.setVersion(version);

        boolean updateAccess = accessService.updateModelByMid(accessModel, paramUpdateAccessBO.getAccessMid());

        log.debug("update access state : [{}]  version: [{}] paramUpdateAccessBO : [{}]", updateAccess, version, paramUpdateAccessBO);

        return updateAccess;
    }


    @Override
    public boolean deleteAccess(@NotNull Long accessMid) {
        return accessService.removeByMid(accessMid);
    }


    @Override
    public List<AccessBucketModel> getAccessBucketList(@NotNull Long accessMid) {
        AccessModel accessModel = accessService.getByMid(accessMid);
        //check accessModel is null
        if (accessModel == null) return new ArrayList<>();

        //query access and sort
        return accessBucketService.lambdaQuery()
                .eq(AccessBucketModel::getAccessMid, accessMid)
                .orderByAsc(AccessBucketModel::getOrderSort)
                .list();
    }

    @Transactional
    @Override
    public boolean updateAccessBucket(@Valid ParamUpdateAccessBucketBO paramUpdateAccessBucketBO) {
        @NotNull Long accessMid = paramUpdateAccessBucketBO.getAccessMid();
        AccessModel accessModel = getAccessModel(accessMid).orElseThrow(() -> new MinOssMessageException("要更新的Access 是不存在的！"));
        //query access bucket list
        List<AccessBucketModel> accessBucketModelList = accessBucketService.getByAccessMid(accessMid);
        //insert new access bucket model

        if (!paramUpdateAccessBucketBO.getBucketMid().isEmpty()) {
            //insert access bucket model
            List<AccessBucketModel> insertAccessBucketModes = new ArrayList<>();
            int orderSort = 0;
            for (Long bucketMid : paramUpdateAccessBucketBO.getBucketMid()) {
                insertAccessBucketModes.add(
                        new AccessBucketModel()
                                .setAccessMid(accessMid)
                                .setBucketMid(bucketMid)
                                .setOrderSort(orderSort++)
                                .setMid(IdGeneratorCore.generatorId())
                );
            }
            if (!accessBucketService.saveBatch(insertAccessBucketModes))
                throw new MinOssMessageException("保存 Access 授权出错！");
        }

        //remove old access bucket
        if (!accessBucketModelList.isEmpty()) {
            boolean removeByMids = accessBucketService.removeByMids(accessBucketModelList.stream().map(AccessBucketModel::getMid).collect(Collectors.toList()));
            if (!removeByMids) throw new MinOssMessageException("移除旧的Access 授权出错！");
        }
        return true;
    }

}
