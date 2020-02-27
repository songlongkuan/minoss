package io.javac.minoss.minoss.minossappservice;

import io.javac.minoss.minosscommon.model.jwt.JwtAuthModel;
import io.vertx.core.http.HttpServerFileUpload;

/**
 * @author pencilso
 * @date 2020/2/27 10:54 上午
 */
public interface VertxFileService {
    void uploadFile(String bucketName,JwtAuthModel authEntitu, HttpServerFileUpload upload);
}
