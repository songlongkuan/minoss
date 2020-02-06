package io.javac.minoss.minosscommon.model.param;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * insert access param model
 *
 * @author pencilso
 * @date 2020/2/6 8:48 下午
 */
@Data
@Accessors(chain = true)
public class ParamInsertAccessBO {

    /**
     * 是否可以上传 0：不可以  1：可以
     */
    @NotNull(message = "请设置是否可以上传")
    private Boolean ifBucketPut;

    /**
     * 是否可以删除  0：不可以  1：可以
     */
    @NotNull(message = "请设置是否可以删除")
    private Boolean ifBucketDel;

    /**
     * 是否可以获取 0：不可以  1：可以
     */
    @NotNull(message = "请设置是否可以查询")
    private Boolean ifBucketGet;

    /**
     * 是否可以编辑  0：不可以 1：可以
     */
    @NotNull(message = "请设置是否可以编辑")
    private Boolean ifBucketEdit;

    /**
     * 备注
     */
    private String accessRemarks;
}
