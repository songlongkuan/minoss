package io.javac.minoss.minosscommon.event;

import io.javac.minoss.minosscommon.model.bo.FileGeneratorBO;
import io.javac.minoss.minosscommon.model.jwt.JwtAuthModel;
import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * @author pencilso
 * @date 2020/2/27 11:29 上午
 */
@Getter
@ToString
public class UploadSuccessEvent extends ApplicationEvent {

    private final JwtAuthModel jwtAuthModel;
    private final FileGeneratorBO fileGeneratorBO;
    private final String objectName;
    private final Long bucketMid;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public UploadSuccessEvent(Object source,String objectName,Long bucketMid, JwtAuthModel jwtAuthModel, FileGeneratorBO fileGeneratorBO) {
        super(source);
        this.objectName = objectName;
        this.bucketMid = bucketMid;
        this.jwtAuthModel = jwtAuthModel;
        this.fileGeneratorBO = fileGeneratorBO;
    }
}
