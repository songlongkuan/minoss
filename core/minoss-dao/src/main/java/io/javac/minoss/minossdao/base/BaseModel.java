package io.javac.minoss.minossdao.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

/**
 * @author pencilso
 * @date 2020/1/24 11:27 上午
 */
@Data
public class BaseModel {
    /**
     * 主键唯一ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 业务唯一ID
     */
    private Long mid;

    /**
     * 乐观锁版本号
     */
    @Version
    private Integer version;

    /**
     * 数据状态 逻辑删除
     */
    @TableLogic
    private Boolean status;

    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;
}
