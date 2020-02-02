package io.javac.minoss.minossdao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.javac.minoss.minossdao.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文件夹模型
 * </p>
 *
 * @author pencilso
 * @since 2020-02-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("minoss_folder")
public class FolderModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 文件夹名称
     */
    private String name;

    /**
     * 父级文件夹ID
     */
    private Long parentMid;


}
