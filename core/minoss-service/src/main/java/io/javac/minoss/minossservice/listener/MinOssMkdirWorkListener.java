package io.javac.minoss.minossservice.listener;

import io.javac.minoss.minosscommon.config.MinOssProperties;
import io.javac.minoss.minosscommon.toolkit.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Spring Boot Ready
 * Create working folder
 *
 * @author pencilso
 * @date 2020/2/15 2:09 下午
 */
@Slf4j
@Component
public class MinOssMkdirWorkListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private MinOssProperties minOssProperties;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        String[] folderPathArray = {
                minOssProperties.getWorkTempUpload()
        };
        for (String folderPath : folderPathArray) {
            FileUtils.mkdirs(folderPath);
        }
    }


}
