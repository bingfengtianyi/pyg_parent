package com.itheima.pyg.entity;

import java.io.Serializable;
import java.util.Arrays;

public class ZImageResult implements Serializable {
    private String[] dateList;
    private Long[] dataList;

    public ZImageResult(String[] dateList, Long[] dataList) {
        this.dateList = dateList;
        this.dataList = dataList;
    }

    public ZImageResult() {
    }

    public String[] getDateList() {
        return dateList;
    }

    public void setDateList(String[] dateList) {
        this.dateList = dateList;
    }

    public Long[] getDataList() {
        return dataList;
    }

    public void setDataList(Long[] dataList) {
        this.dataList = dataList;
    }

    @Override
    public String toString() {
        return "ZImageResult{" +
                "dateList=" + Arrays.toString(dateList) +
                ", dataList=" + Arrays.toString(dataList) +
                '}';
    }
}
