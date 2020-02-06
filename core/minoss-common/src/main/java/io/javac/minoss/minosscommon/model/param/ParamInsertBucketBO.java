package io.javac.minoss.minosscommon.model.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * insert bucket param model
 *
 * @author pencilso
 * @date 2020/1/27 5:06 下午
 */
@Data
public class ParamInsertBucketBO {

    @NotBlank
    @Length(min = 1, max = 32, message = "bucket 名称长度范围应在 1-32 之间")
    private String bucketName;

    @NotNull(message = "bucket 权限不能为空")
    private Byte bucketRight;

    @NotBlank(message = "bucket 本地存储空间路径不能为空")
    private String bucketStorePath;
}
