package com.itheima.pyg.pojo.good;

import java.io.Serializable;

public class Brand implements Serializable {
    private Long id;

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 品牌首字母
     */
    private String firstChar;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getFirstChar() {
        return firstChar;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar == null ? null : firstChar.trim();
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
        sb.append(", name=").append(name);
        sb.append(", firstChar=").append(firstChar);
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
        Brand other = (Brand) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getFirstChar() == null ? other.getFirstChar() == null : this.getFirstChar().equals(other.getFirstChar()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getFirstChar() == null) ? 0 : getFirstChar().hashCode());
        return result;
    }
}