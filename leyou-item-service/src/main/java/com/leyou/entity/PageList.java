package com.leyou.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class PageList<T> implements Serializable {
    //    全部条数
    private Integer itemsLength;
    //    当前第几条开始
    private Integer pageStart;
    //    当前第几条结束
    private Integer pageStop;
    //    物品
    private List<T> items;

}

