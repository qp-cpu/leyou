package com.leyou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyou.entity.TbSku;
import com.leyou.dao.TbSkuMapper;
import com.leyou.entity.TbSpu;
import com.leyou.entity.TbStock;
import com.leyou.service.TbSkuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * sku表,该表表示具体的商品实体,如黑色的 64g的iphone 8 服务实现类
 * </p>
 *
 * @author qp
 * @since 2020-02-11
 */
@Service
public class TbSkuServiceImpl extends ServiceImpl<TbSkuMapper, TbSku> implements TbSkuService {

    @Override
    public void addsku(TbSku sku) {
        baseMapper.insert( sku );
    }

    @Override
    public List<TbSku> QuerySkuBySpuId(Long spuid) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq( "spu_id" ,spuid);
        List<TbSku> list = baseMapper.selectList( wrapper );
        return list;
    }

    /**
     * 根据spuid查询skuid
     * @param id
     */
    @Override
    public List<Long> qureySkuidBySpuid(Long id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq( "spu_id",id );
        List<TbSku> list = baseMapper.selectList( wrapper );
        List<Long>  collect = list.stream().map( tbSku -> tbSku.getId() ).collect( Collectors.toList() );
        return collect;
    }

    /**
     * 删除skubyids
     * @param skuids
     */
    @Override
    public void deleteBySkuids(List<Long> skuids) {
        baseMapper.deleteBatchIds( skuids );
    }

    /**
     * 根据skuid 查询 sku
     * @param skuid
     * @return
     */
    @Override
    public TbSku queryById(Long skuid) {

        return  this.baseMapper.selectById( skuid );
    }
}
