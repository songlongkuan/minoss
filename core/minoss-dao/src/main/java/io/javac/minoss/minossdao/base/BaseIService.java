package io.javac.minoss.minossdao.base;

import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * @author pencilso
 * @date 2020/1/24 11:29 上午
 */
public interface BaseIService<M extends BaseModel> extends IService<M> {

    /**
     * 根据业务唯一ID查询
     *
     * @param mid
     * @return
     */
    default M getByMid(@NotNull Long mid) {
        return lambdaQuery().eq(BaseModel::getMid, mid).one();
    }

    /**
     * 批量查询业务ID
     *
     * @param mids
     * @return
     */
    default List<M> getByMids(@NotNull Collection<Long> mids) {
        return lambdaQuery().in(BaseModel::getMid, mids).list();
    }

    /**
     * 根据业务ID删除
     *
     * @param mid
     * @return
     */
    default boolean removeByMid(@NotNull Long mid) {
        return lambdaUpdate().eq(BaseModel::getMid, mid).remove();
    }
}
