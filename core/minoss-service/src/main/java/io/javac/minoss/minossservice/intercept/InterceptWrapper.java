package io.javac.minoss.minossservice.intercept;

import io.javac.minoss.minossservice.base.BaseInterceptHandler;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;

/**
 * 拦截器配置
 *
 * @author pencilso
 * @date 2020/1/25 11:04 上午
 */
public class InterceptWrapper {
    private List<InterceptWrapperPojo> pojoList;


    public InterceptWrapper() {
        pojoList = new LinkedList<InterceptWrapperPojo>();
    }

    public InterceptWrapper addIntercept(InterceptWrapperPojo interceptWrapperPojo) {
        pojoList.add(interceptWrapperPojo);
        return this;
    }

    public List<InterceptWrapperPojo> getPojoList() {
        return pojoList;
    }

    @Data
    @Accessors(chain = true)
    public static class InterceptWrapperPojo {
        private String apiPath;
        private Class<? extends BaseInterceptHandler> interceptHandler;
    }


}
