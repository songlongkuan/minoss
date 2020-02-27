package io.javac.minoss.minossdao.mapper;

import io.javac.minoss.minossdao.model.BucketCollectModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * Bucket 汇总表 （各数据的统计） Mapper 接口
 * </p>
 *
 * @author pencilso
 * @since 2020-01-25
 */
public interface BucketCollectMapper extends BaseMapper<BucketCollectModel> {
    /**
     * incr store file size
     *
     * @param bucketMid where bucket mid
     * @param incr      incr number
     * @return
     */
    boolean incrFileSize(@Param("bucketMid") @NotNull Long bucketMid, @Param("incr") @NotNull int incr);
    /**
     * incr store used size
     *
     * @param bucketMid where bucket mid
     * @param incr      incr file leng
     * @return
     */
    boolean incrUsedSize(@Param("bucketMid") @NotNull Long bucketMid, @Param("incr") @NotNull long incr);
}
