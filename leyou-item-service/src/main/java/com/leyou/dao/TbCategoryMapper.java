package com.leyou.dao;

import com.leyou.entity.TbCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 商品类目表，类目和商品(spu)是一对多关系，类目与品牌是多对多关系 Mapper 接口
 * </p>
 *
 * @author qp
 * @since 2020-01-09
 */
@Mapper
public interface TbCategoryMapper extends BaseMapper<TbCategory> {

}
