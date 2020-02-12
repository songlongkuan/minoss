package io.javac.minoss.minosscommon.model.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author pencilso
 * @date 2020/2/9 9:51 下午
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class FileGeneratorBO {
    private String fileName;
    private String fileExt;
    private String dir;
    private String path;
}
