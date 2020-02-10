package com.leyou.dao;

import com.leyou.entity.TbSpecGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 规格参数的分组表，每个商品分类下有多个规格参数组 Mapper 接口
 * </p>
 *
 * @author qp
 * @since 2020-02-09
 */
@Mapper
public interface TbSpecGroupMapper extends BaseMapper<TbSpecGroup> {

}
