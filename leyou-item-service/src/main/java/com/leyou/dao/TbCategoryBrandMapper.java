package com.leyou.dao;

import com.leyou.entity.TbCategoryBrand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 商品分类和品牌的中间表，两者是多对多关系 Mapper 接口
 * </p>
 *
 * @author qp
 * @since 2020-01-14
 */
public interface TbCategoryBrandMapper extends BaseMapper<TbCategoryBrand> {

}
