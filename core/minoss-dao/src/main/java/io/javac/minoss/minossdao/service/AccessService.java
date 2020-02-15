package io.javac.minoss.minossdao.service;

import io.javac.minoss.minossdao.model.AccessModel;
import io.javac.minoss.minossdao.base.BaseIService;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * <p>
 * bucket 授权 服务类
 * </p>
 *
 * @author pencilso
 * @since 2020-02-06
 */
public interface AccessService extends BaseIService<AccessModel> {
    /**
     * query access model by access key
     *
     * @param accessKey
     * @return
     */
    Optional<AccessModel> getByAccessKey(@NotNull String accessKey);
}
