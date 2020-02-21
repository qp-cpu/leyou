package com.leyou.dao;

import com.leyou.entity.TbStock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 库存表，代表库存，秒杀库存等信息 Mapper 接口
 * </p>
 *
 * @author qp
 * @since 2020-02-11
 */
@Mapper
public interface TbStockMapper extends BaseMapper<TbStock> {

}
