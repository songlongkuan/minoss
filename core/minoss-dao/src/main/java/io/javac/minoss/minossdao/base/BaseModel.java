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
     * 是否删除  0未删除  1已删除
     */
    @TableLogic
    private Boolean ifDel;

    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public Long getMid() {
        return mid;
    }

    public Integer getVersion() {
        return version;
    }

    public Boolean getIfDel() {
        return ifDel;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }


    public <T extends BaseModel> T setId(Long id) {
        this.id = id;
        return (T) this;
    }

    public <T extends BaseModel> T setMid(Long mid) {
        this.mid = mid;
        return (T) this;
    }

    public <T extends BaseModel> T setVersion(Integer version) {
        this.version = version;
        return (T) this;
    }

    public <T extends BaseModel> T setIfDel(Boolean ifDel) {
        this.ifDel = ifDel;
        return (T) this;
    }

    public <T extends BaseModel> T setCreateDate(Date createDate) {
        this.createDate = createDate;
        return (T) this;
    }

    public <T extends BaseModel> T setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
        return (T) this;
    }
}
