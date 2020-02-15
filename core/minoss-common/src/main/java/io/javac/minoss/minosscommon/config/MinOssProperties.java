package io.javac.minoss.minosscommon.config;

import io.javac.minoss.minosscommon.constant.MinOssConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;

/**
 * @author pencilso
 * @date 2020/1/23 8:07 下午
 */
@Data
@ConfigurationProperties(prefix = "minoss")
public class MinOssProperties {
    private int port = 8080;

    /**
     * working dir
     */
    private String workDir = (MinOssConst.USER_HOME.endsWith(File.separator) ? MinOssConst.USER_HOME : MinOssConst.USER_HOME + File.separator)
            + ".minoss" + File.separator;

    /**
     * request respone ....  logs out
     */
    private boolean devlog = false;

    /**
     * upload file temp
     */
    private String workTempUpload = workDir + "upload/";




}
