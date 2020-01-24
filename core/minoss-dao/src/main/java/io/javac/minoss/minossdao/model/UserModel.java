package io.javac.minoss.minossdao.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.javac.minoss.minossdao.base.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author pencilso
 * @since 2020-01-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("minoss_user")
public class UserModel extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String loginPassword;

    /**
     * 昵称
     */
    private String nikeName;

    /**
     * token 令牌
     */
    private String jwtToken;

    /**
     * token令牌哈希盐
     */
    private String jwtSalt;


}
