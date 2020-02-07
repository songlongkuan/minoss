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
public class ParamUpdateAccessBO extends ParamInsertAccessBO {

    /**
     * access mid
     */
    @NotNull(message = "access mid 不能为空")
    private Long accessMid;
}
