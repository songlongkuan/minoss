package io.javac.minoss.minoss.minossappservice.impl;

import io.javac.minoss.minoss.minossappservice.VertxBucketService;
import io.javac.minoss.minoss.minossappservice.VertxFileService;
import io.javac.minoss.minosscommon.model.bo.FileGeneratorBO;
import io.javac.minoss.minosscommon.model.jwt.JwtAuthModel;
import io.javac.minoss.minosscommon.toolkit.FileUtils;
import io.javac.minoss.minossdao.model.BucketModel;
import io.vertx.core.http.HttpServerFileUpload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pencilso
 * @date 2020/2/27 10:54 上午
 */
@Slf4j
@Service
public class VertxFileServiceImpl implements VertxFileService {
    @Autowired
    private VertxBucketService vertxBucketService;

    @Override
    public void uploadFile(String bucketName, JwtAuthModel authEntitu, HttpServerFileUpload upload) {
        BucketModel bucketModel = vertxBucketService.getBucketModelByCache(bucketName);
        String storePath = bucketModel.getBucketStorePath().endsWith("/") ? bucketModel.getBucketStorePath() : bucketModel.getBucketStorePath() + "/";
        //get file ext name
        String fileExt = FileUtils.getFileExt(upload.filename());
        FileGeneratorBO fileGeneratorBO = FileUtils.generatorFilePath(storePath, fileExt);
        log.debug("upload file -> [{}]", fileGeneratorBO);
        //save file
        upload.streamToFileSystem(fileGeneratorBO.getPath());
    }
}
