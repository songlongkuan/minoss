package io.javac.minoss.minossappclient;

import io.javac.minoss.minoss.minossappservice.VertxFileService;
import io.javac.minoss.minosscommon.annotation.RequestBody;
import io.javac.minoss.minosscommon.annotation.RequestMapping;
import io.javac.minoss.minosscommon.model.jwt.JwtAuthModel;
import io.javac.minoss.minossservice.base.VertxControllerHandler;
import io.javac.minoss.minosscommon.config.MinOssProperties;
import io.javac.minoss.minosscommon.enums.request.RequestMethod;
import io.javac.minoss.minosscommon.model.bo.FileGeneratorBO;
import io.javac.minoss.minosscommon.toolkit.FileUtils;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author pencilso
 * @date 2020/2/9 8:59 下午
 */
@Slf4j
@Component
@RequestMapping("/api/client/file")
public class VertxFileController {
    @Autowired
    private VertxFileService vertxFileService;

    @RequestMapping(value = "uploadfile", method = RequestMethod.POST)
    public VertxControllerHandler uploadFile() {
        return vertxRequest -> {
            String bucketName = vertxRequest.getParamNotBlank("bucketName");
            JwtAuthModel authEntitu = vertxRequest.getAuthEntitu();

            RoutingContext routingContext = vertxRequest.getRoutingContext();
            HttpServerRequest request = routingContext.request();

            request.setExpectMultipart(true);
            request.uploadHandler(upload -> {
                vertxFileService.uploadFile(bucketName, authEntitu, upload);
            });
            vertxRequest.buildVertxRespone().responeState(true);
        };
    }
}
