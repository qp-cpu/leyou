package com.leyou.dao;

import com.leyou.entity.TbBrand;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 品牌表，一个品牌下有多个商品（spu），一对多关系 Mapper 接口
 * </p>
 *
 * @author qp
 * @since 2020-01-10
 */
public interface TbBrandMapper extends BaseMapper<TbBrand> {

    List<TbBrand> queryByPageandRows(@Param( "page" ) Integer page,@Param( "rows" ) Integer rows);

    List<TbBrand> queryByPageandRowsandKey(Integer page,Integer rows);

    @Select( "select * from tb_brand a INNER JOIN tb_category_brand b on a.id =b.brand_id where b.category_id =#{cid}" )
    List<TbBrand> queryBytbCatagroyids(@Param( "cid" ) Long cid);
}
