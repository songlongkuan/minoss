package io.javac.minoss.minossservice.controller;

import io.javac.minoss.minosscommon.model.bo.FileGeneratorBO;
import io.javac.minoss.minosscommon.model.jwt.JwtAuthModel;
import io.vertx.core.http.HttpServerFileUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author pencilso
 * @date 2020/2/27 10:54 上午
 */
public interface VertxFileService {
    void uploadFile(String bucketName, String objectName, JwtAuthModel authEntitu, HttpServerFileUpload upload);

    boolean insertFileByFileGeneratorBO(@NotBlank String objectName, @NotNull Long accessMid, @NotNull FileGeneratorBO fileGeneratorBO);
}
