package com.leyou.service;

import com.leyou.entity.TbCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品类目表，类目和商品(spu)是一对多关系，类目与品牌是多对多关系 服务类
 * </p>
 *
 * @author qp
 * @since 2020-01-09
 */
public interface TbCategoryService extends IService<TbCategory> {


    List<TbCategory> queryTbCategoryByPid(Long pid);

    boolean addCategory(TbCategory tbCategory);

    boolean deleteByTbCategoryId(Long id);

    List<String> selectBycid(ArrayList<Long> list);

    List<String> queryTbCategoryByids(List<Long> ids);
}
