package com.leyou.service;

import com.leyou.entity.TbCategoryBrand;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品分类和品牌的中间表，两者是多对多关系 服务类
 * </p>
 *
 * @author qp
 * @since 2020-01-14
 */
public interface TbCategoryBrandService extends IService<TbCategoryBrand> {

    void addCategoryBrand(Long bid,Long cid);
}
