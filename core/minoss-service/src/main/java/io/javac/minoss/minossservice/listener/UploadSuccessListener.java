package io.javac.minoss.minossservice.listener;

import io.javac.minoss.minosscommon.event.UploadSuccessEvent;
import io.javac.minoss.minosscommon.model.bo.FileGeneratorBO;
import io.javac.minoss.minosscommon.model.jwt.JwtAuthModel;
import io.javac.minoss.minossservice.controller.VertxBucketService;
import io.javac.minoss.minossservice.controller.VertxFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * file upload server success listener
 *
 * @author pencilso
 * @date 2020/2/27 11:26 上午
 */
@Slf4j
@Component
public class UploadSuccessListener implements ApplicationListener<UploadSuccessEvent> {
    @Autowired
    private VertxBucketService vertxBucketService;
    @Autowired
    private VertxFileService vertxFileService;

    @Override
    public void onApplicationEvent(UploadSuccessEvent event) {
        log.info("receive upload success listener event: [{}]", event);

        JwtAuthModel jwtAuthModel = event.getJwtAuthModel();
        FileGeneratorBO fileGeneratorBO = event.getFileGeneratorBO();

        File file = new File(fileGeneratorBO.getPath());

        vertxFileService.insertFileByFileGeneratorBO(event.getObjectName(), jwtAuthModel.getId(), fileGeneratorBO);
        vertxBucketService.incrBucketCollectFileSize(event.getBucketMid(), 1);
        vertxBucketService.incrBucketCollectUsedSize(event.getBucketMid(), file.length());

    }
}
