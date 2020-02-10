package com.leyou.service;

import com.leyou.entity.TbSpecGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 规格参数的分组表，每个商品分类下有多个规格参数组 服务类
 * </p>
 *
 * @author qp
 * @since 2020-02-09
 */
public interface TbSpecGroupService extends IService<TbSpecGroup> {

    List<TbSpecGroup> queryGroupByCid(Long cid);

    int addTbSpecgroup(TbSpecGroup tbSpecGroup);

    int updateTbSpecgroup(TbSpecGroup tbSpecGroup);

    int deleteTbSpecgroup(Long id);
}
