package com.wondersgroup.healthcloud.utils;

import java.util.List;
import java.util.Map;

/**
 * Created by nqz on 2016/3/26.
 */
public class Pager {

    private int totalPages;//总页数
    private int totalElements;//总条数
    private int size;//页大小
    private int number;//页码
    private Map parameter;//参数
    private List data;//对象数据集合

    public int getTotalPages() {
        return (int) Math.ceil(this.totalElements * 1.0 / size);
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Map getParameter() {
        return parameter;
    }

    public void setParameter(Map parameter) {
        this.parameter = parameter;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
