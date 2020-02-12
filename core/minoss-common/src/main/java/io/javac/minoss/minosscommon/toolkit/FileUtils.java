package io.javac.minoss.minosscommon.toolkit;

import io.javac.minoss.minosscommon.model.bo.FileGeneratorBO;
import io.javac.minoss.minosscommon.toolkit.id.IdGeneratorCore;
import org.springframework.util.StringUtils;

/**
 * @author pencilso
 * @date 2020/2/9 9:35 下午
 */
public class FileUtils {


    /**
     * get file extension name
     * but file not ext -> return empty
     *
     * @return
     */
    public static String getFileExt(String fileName) {
        Assert.notBlank(fileName, "getFileExt file name can not be null");
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) return "";
        return fileName.substring(lastIndexOf + 1);
    }

    /**
     * generator new file path
     *
     * @param dir     store folder path
     * @param fileExt store file extension name
     * @return
     */
    public static FileGeneratorBO generatorFilePath(String dir, String fileExt) {
        Assert.notBlank(dir, "generatorFilePath dir can not blank");
        String fileName = null;
        if (StringUtils.isEmpty(fileExt)) {
            fileName = IdGeneratorCore.generatorUUID();
        } else {
            fileName = IdGeneratorCore.generatorUUID() + "." + fileExt;
        }
        FileGeneratorBO fileGeneratorBO = new FileGeneratorBO();
        fileGeneratorBO.setDir(dir);
        fileGeneratorBO.setFileExt(fileExt);
        fileGeneratorBO.setFileName(fileName);
        fileGeneratorBO.setPath(dir + fileName);
        return fileGeneratorBO;
    }
}
