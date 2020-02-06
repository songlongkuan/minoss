package io.javac.minoss.minoss.minossappservice.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.javac.minoss.minoss.minossappservice.VertxAccessService;
import io.javac.minoss.minosscommon.bcrypt.PasswordEncoder;
import io.javac.minoss.minosscommon.model.param.ParamInsertAccessBO;
import io.javac.minoss.minosscommon.model.vo.AccessVO;
import io.javac.minoss.minosscommon.toolkit.id.IdGeneratorCore;
import io.javac.minoss.minossdao.model.AccessModel;
import io.javac.minoss.minossdao.service.AccessBucketService;
import io.javac.minoss.minossdao.service.AccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;

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
            log.info("source bean: [{}]", it);

            AccessVO accessVO = dto2vo(it);
            responePage.getRecords().add(accessVO);

            log.info("target bean: [{}]", accessVO);
        });
        //respone
        return responePage;
    }

    @Override
    public AccessVO dto2vo(AccessModel accessModel) {
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
}
