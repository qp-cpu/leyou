package com.leyou.service;

import com.leyou.entity.TbStock;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 库存表，代表库存，秒杀库存等信息 服务类
 * </p>
 *
 * @author qp
 * @since 2020-02-11
 */
public interface TbStockService extends IService<TbStock> {

    void addstock(TbStock tbStock);


    void deleteBySkuids(List<Long> skuids);
}
