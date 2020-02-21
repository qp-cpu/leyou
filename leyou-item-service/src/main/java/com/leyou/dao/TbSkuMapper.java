package com.leyou.dao;

import com.leyou.entity.TbSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * sku表,该表表示具体的商品实体,如黑色的 64g的iphone 8 Mapper 接口
 * </p>
 *
 * @author qp
 * @since 2020-02-11
 */
@Mapper
public interface TbSkuMapper extends BaseMapper<TbSku> {

}
