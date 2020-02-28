package io.javac.minoss.minossservice.controller.impl;

import io.javac.minoss.minosscommon.config.MinOssProperties;
import io.javac.minoss.minosscommon.exception.MinOssMessageException;
import io.javac.minoss.minosscommon.model.bo.FileGeneratorBO;
import io.javac.minoss.minosscommon.model.jwt.JwtAuthModel;
import io.javac.minoss.minosscommon.toolkit.FileUtils;
import io.javac.minoss.minosscommon.toolkit.ShellToolkit;
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
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

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
    @Autowired
    private MinOssProperties minOssProperties;

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


        String fileMd5 = getFileMd5(fileGeneratorBO.getPath()).orElseThrow(() -> new MinOssMessageException("get file md5 error"));

        fileModel.setFileExt(fileGeneratorBO.getFileExt())
                .setAccessMid(accessMid)
                .setFileMd5(fileMd5)
                .setFileNameStore(fileGeneratorBO.getFileName())
                .setFilePathMd5(DigestUtils.md5DigestAsHex(objectName.getBytes(Charset.defaultCharset())))
                .setMid(IdGeneratorCore.generatorId());

        return fileService.save(fileModel);
    }

    @Override
    public Optional<String> getFileMd5(@NotBlank String filePath) {
        File file = new File(filePath);
        if (!file.exists()){
            throw new MinOssMessageException("get file md5 fail , path no exists . ");
        }

        AtomicReference<String> cmdReturn = new AtomicReference<>();
        switch (minOssProperties.getOsType()) {
            case Linux: {
                String shell = "md5sum " + filePath;
                Optional<String> execCmd = ShellToolkit.execCmd(shell, null);
                execCmd.ifPresent(respone -> {
                    cmdReturn.set(respone.substring(0, 32));
                });
            }
            break;
            case MacOS: {
                String shell = "md5 " + filePath;
                Optional<String> execCmd = ShellToolkit.execCmd(shell, null);
                execCmd.ifPresent(respone -> {
                    String[] split = respone.split("=");
                    if (split.length > 1) {
                        cmdReturn.set(split[1].trim());
                    }
                });
            }
            break;
            case Windows: {
                String shell = "certutil -hashfile " + filePath + " MD5";
                Optional<String> execCmd = ShellToolkit.execCmd(shell, null);
                execCmd.ifPresent(respone -> {
                    String[] split = respone.split("\n");
                    if (split.length >= 3) {
                        cmdReturn.set(split[1].trim());
                    }
                });
            }
            break;
            default:
                throw new MinOssMessageException("unknown os type");
        }
        if (StringUtils.isEmpty(cmdReturn.get())) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(cmdReturn.get());
        }
    }


}
