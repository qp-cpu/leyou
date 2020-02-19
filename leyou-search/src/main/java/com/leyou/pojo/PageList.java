package com.leyou.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class PageList<T> implements Serializable {
    private Long total;
    private Integer totalPage;
    private List<T> items;

    public PageList(Long total,Integer totalPage,List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }
}

