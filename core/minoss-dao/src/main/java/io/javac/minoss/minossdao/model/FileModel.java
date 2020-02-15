package io.javac.minoss.minossdao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.javac.minoss.minossdao.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author pencilso
 * @since 2020-02-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("minoss_file")
public class FileModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 原始文件名
     */
    private String fileName;

    /**
     * 文件名 - 存储到服务器上的本地文件名
     */
    private String fileNameStore;

    /**
     * 文件后缀
     */
    private String fileExt;

    /**
     * 文件md5
     */
    private String fileMd5;

    /**
     * 文件访问路径
     */
    private String filePath;

    /**
     * 文件访问路径的md5
     */
    private String filePathMd5;

    /**
     * 文件夹id
     */
    private Long folderMid;
    /**
     * 授权key的唯一ID
     */
    private Long accessMid;
}
