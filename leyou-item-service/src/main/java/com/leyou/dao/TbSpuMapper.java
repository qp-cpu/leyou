package com.leyou.dao;

import com.leyou.entity.TbSpu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * spu表，该表描述的是一个抽象性的商品，比如 iphone8 Mapper 接口
 * </p>
 *
 * @author qp
 * @since 2020-02-10
 */
@Mapper
public interface TbSpuMapper extends BaseMapper<TbSpu> {

}
