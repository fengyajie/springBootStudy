package com.example.job;

import java.util.Date;

/**
 * @author fyj
 */
public class EcmJdConfig {

    private Integer id;

    private Integer skuCount;

    private Integer delJd;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSkuCount() {
        return skuCount;
    }

    public void setSkuCount(Integer skuCount) {
        this.skuCount = skuCount;
    }

    public Integer getDelJd() {
        return delJd;
    }

    public void setDelJd(Integer delJd) {
        this.delJd = delJd;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
