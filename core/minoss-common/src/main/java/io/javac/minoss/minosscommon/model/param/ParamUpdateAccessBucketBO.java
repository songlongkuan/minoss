package io.javac.minoss.minosscommon.model.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * insert access bucket param model
 *
 * @author pencilso
 * @date 2020/2/9 9:34 上午
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
public class ParamUpdateAccessBucketBO {

    @NotNull
    private Long accessMid;

    private List<Long> bucketMid;
}
