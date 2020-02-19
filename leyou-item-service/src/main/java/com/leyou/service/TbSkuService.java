package com.leyou.service;

import com.leyou.entity.TbSku;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leyou.entity.TbSpu;

import java.util.List;

/**
 * <p>
 * sku表,该表表示具体的商品实体,如黑色的 64g的iphone 8 服务类
 * </p>
 *
 * @author qp
 * @since 2020-02-11
 */
public interface TbSkuService extends IService<TbSku> {

    void addsku(TbSku sku);

    List<TbSku> QuerySkuBySpuId(Long spuid);

    List<Long> qureySkuidBySpuid(Long id);

    void deleteBySkuids(List<Long> skuids);
}
