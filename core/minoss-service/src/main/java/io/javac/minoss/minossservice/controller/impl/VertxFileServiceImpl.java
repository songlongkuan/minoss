package io.javac.minoss.minossservice.controller.impl;

import io.javac.minoss.minosscommon.model.bo.FileGeneratorBO;
import io.javac.minoss.minosscommon.model.jwt.JwtAuthModel;
import io.javac.minoss.minosscommon.toolkit.FileUtils;
import io.javac.minoss.minosscommon.toolkit.id.IdGeneratorCore;
import io.javac.minoss.minossdao.model.BucketModel;
import io.javac.minoss.minossdao.model.FileModel;
import io.javac.minoss.minossdao.service.FileService;
import io.javac.minoss.minossservice.controller.VertxBucketService;
import io.javac.minoss.minossservice.controller.VertxFileService;
import io.javac.minoss.minosscommon.event.UploadSuccessEvent;
import io.vertx.core.http.HttpServerFileUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.nio.charset.Charset;

/**
 * @author pencilso
 * @date 2020/2/27 10:54 上午
 */
@Slf4j
@Service
public class VertxFileServiceImpl implements VertxFileService {
    @Autowired
    private VertxBucketService vertxBucketService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private FileService fileService;

    @Override
    public void uploadFile(String bucketName, String objectName, JwtAuthModel authEntitu, HttpServerFileUpload upload) {
        BucketModel bucketModel = vertxBucketService.getBucketModelByCache(bucketName);
        String storePath = bucketModel.getBucketStorePath().endsWith("/") ? bucketModel.getBucketStorePath() : bucketModel.getBucketStorePath() + "/";
        //get file ext name
        String fileExt = FileUtils.getFileExt(objectName);
        FileGeneratorBO fileGeneratorBO = FileUtils.generatorFilePath(storePath, fileExt);
        log.debug("upload file -> [{}]", fileGeneratorBO);
        //save file
        upload.streamToFileSystem(fileGeneratorBO.getPath());
        upload.endHandler(endHandler -> {
            //send event
            UploadSuccessEvent uploadSuccessEvent = new UploadSuccessEvent(this, objectName, bucketModel.getMid(), authEntitu, fileGeneratorBO);
            applicationContext.publishEvent(uploadSuccessEvent);
            log.debug("upload file success publishevent: [{}]", uploadSuccessEvent);
        });
        upload.exceptionHandler(errorHandler -> {
            log.error("upload file error ", errorHandler);
        });
    }

    @Override
    public boolean insertFileByFileGeneratorBO(@NotBlank String objectName, @NotNull Long accessMid, @NotNull FileGeneratorBO fileGeneratorBO) {
        FileModel fileModel = new FileModel();

        //get file ext name
        if (objectName.startsWith("/")) {
            //remove start "/"
            objectName = objectName.substring(1);
        }


        if (objectName.contains("/")) {
            int lastIndexOf = objectName.lastIndexOf("/");
            fileModel.setFileName(objectName.substring(lastIndexOf + 1));
        } else {
            fileModel.setFileName(objectName);
        }
        fileModel.setFilePath(objectName);


        String fileMd5 = FileUtils.getFileMd5(fileGeneratorBO.getPath());


        fileModel.setFileExt(fileGeneratorBO.getFileExt())
                .setAccessMid(accessMid)
                .setFileMd5(fileMd5)
                .setFileNameStore(fileGeneratorBO.getFileName())
                .setFilePathMd5(DigestUtils.md5DigestAsHex(objectName.getBytes(Charset.defaultCharset())))
                .setMid(IdGeneratorCore.generatorId());

        return fileService.save(fileModel);
    }


}
