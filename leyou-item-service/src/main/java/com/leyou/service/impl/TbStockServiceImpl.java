package com.leyou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyou.entity.TbStock;
import com.leyou.dao.TbStockMapper;
import com.leyou.service.TbStockService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 库存表，代表库存，秒杀库存等信息 服务实现类
 * </p>
 *
 * @author qp
 * @since 2020-02-11
 */
@Service
public class TbStockServiceImpl extends ServiceImpl<TbStockMapper, TbStock> implements TbStockService {

    /**
     * 新增stock
     * @param tbStock
     */
    @Override
    public void addstock(TbStock tbStock) {
        baseMapper.insert( tbStock );
    }

    @Override
    public void deleteBySkuids(List<Long> skuids) {
         baseMapper.deleteBatchIds( skuids );
    }


}
