package com.itheima.pyg.pojo.specification;

import java.io.Serializable;

public class Specification implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String specName;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName == null ? null : specName.trim();
    }

    /**
     * revise start: 1 新增商家ID字段,即商家名称 2 添加get set方法 3 tostring方法添加sellerId字段   gengweiwei
     */
    //修改1 新增商家ID字段,即商家名称
    private String sellerId;

    //修改2 添加get set方法
    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", specName=").append(specName);
        //修改3 tostring方法添加sellerId字段
        sb.append(", sellerId=").append(sellerId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    /**
     * revise end: 1 新增商家ID字段,即商家名称 2 添加get set方法 3 tostring方法添加sellerId字段   gengweiwei
     */

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Specification other = (Specification) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSpecName() == null ? other.getSpecName() == null : this.getSpecName().equals(other.getSpecName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSpecName() == null) ? 0 : getSpecName().hashCode());
        return result;
    }
}