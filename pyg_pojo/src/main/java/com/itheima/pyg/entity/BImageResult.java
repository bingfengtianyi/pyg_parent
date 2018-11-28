package com.itheima.pyg.entity;

import java.io.Serializable;
import java.util.Arrays;

public class BImageResult   implements Serializable {
    private String[] sellerList;
    private Long[] moneyList;

    public BImageResult(Long[] moneyList) {
        this.moneyList = moneyList;
    }

    public BImageResult() {
    }

    public String[] getSellerList() {
        return sellerList;
    }

    public void setSellerList(String[] sellerList) {
        this.sellerList = sellerList;
    }

    public Long[] getMoneyList() {
        return moneyList;
    }

    public void setMoneyList(Long[] moneyList) {
        this.moneyList = moneyList;
    }

    @Override
    public String toString() {
        return "BImageResult{" +
                "sellerList=" + Arrays.toString(sellerList) +
                ", moneyList=" + Arrays.toString(moneyList) +
                '}';
    }
}
