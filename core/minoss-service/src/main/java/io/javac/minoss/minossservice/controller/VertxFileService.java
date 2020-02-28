package io.javac.minoss.minossservice.controller;

import io.javac.minoss.minosscommon.model.bo.FileGeneratorBO;
import io.javac.minoss.minosscommon.model.jwt.JwtAuthModel;
import io.vertx.core.http.HttpServerFileUpload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @author pencilso
 * @date 2020/2/27 10:54 上午
 */
public interface VertxFileService {
    /**
     * upload file
     *
     * @param bucketName bucket name
     * @param objectName object name (xxx/xxx/xxxx.png)
     * @param authEntitu authentity
     * @param upload     upload
     */
    void uploadFile(String bucketName, String objectName, JwtAuthModel authEntitu, HttpServerFileUpload upload);

    /**
     * insert file data
     *
     * @param objectName      object name
     * @param accessMid       accessmid (accesskey mid)
     * @param fileGeneratorBO fileinfo
     * @return
     */
    boolean insertFileByFileGeneratorBO(@NotBlank String objectName, @NotNull Long accessMid, @NotNull FileGeneratorBO fileGeneratorBO);

    /**
     * get file md5 hash by local file
     *
     * @param filePath file local path
     * @return
     */
    Optional<String> getFileMd5(@NotBlank String filePath);
}
