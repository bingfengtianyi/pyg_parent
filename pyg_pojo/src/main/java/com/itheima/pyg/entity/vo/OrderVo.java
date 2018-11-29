package com.itheima.pyg.entity.vo;

import com.itheima.pyg.pojo.order.OrderItem;

import java.io.Serializable;
import java.util.List;

public class OrderVo implements Serializable {
    private String createTime;//订单创建时间

    private String  orderId; //订单编号

    private String nickName;//店铺名称

    private List<OrderItem> orderItemList;//订单下商品集合

    public OrderVo() {
    }

    public OrderVo(String createTime, String orderId, String nickName, List<OrderItem> orderItemList) {
        this.createTime = createTime;
        this.orderId = orderId;
        this.nickName = nickName;
        this.orderItemList = orderItemList;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
