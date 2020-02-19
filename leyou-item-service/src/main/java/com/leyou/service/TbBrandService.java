package com.leyou.service;

import com.leyou.entity.PageList;
import com.leyou.entity.TbBrand;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 品牌表，一个品牌下有多个商品（spu），一对多关系 服务类
 * </p>
 *
 * @author qp
 * @since 2020-01-10
 */
public interface TbBrandService extends IService<TbBrand> {

   PageList<TbBrand> queryBrandssByPage(String key,Integer page,Integer rows,String sortBy,Boolean desc);

   boolean deleteById(Long id);

   boolean addBrand(TbBrand tbBrand);

    TbBrand selectByBid(Long brandId);

    List<TbBrand> queryBrandBybids(List<Long> bids);

    List<TbBrand> queryBrandsBycid(Long cid);

    TbBrand queryBrandByid(Long id);
}
