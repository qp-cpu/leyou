package com.leyou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyou.entity.TbCategoryBrand;
import com.leyou.dao.TbCategoryBrandMapper;
import com.leyou.service.TbCategoryBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品分类和品牌的中间表，两者是多对多关系 服务实现类
 * </p>
 *
 * @author qp
 * @since 2020-01-14
 */
@Service
public class TbCategoryBrandServiceImpl extends ServiceImpl<TbCategoryBrandMapper, TbCategoryBrand> implements TbCategoryBrandService {

    /**
     * 添加商品品牌中间表
     * @param bid
     * @param cid
     */
    @Override
    public void addCategoryBrand(Long bid,Long cid) {
        TbCategoryBrand tbCategoryBrand=new TbCategoryBrand();
        tbCategoryBrand.setBrandId( bid );
        tbCategoryBrand.setCategoryId( cid );
        int insert = baseMapper.insert( tbCategoryBrand );
    }

    /**
     * 根据cid查询品牌id
     * @param cid
     * @return
     */
    @Override
    public List<Long> qureyByCid(Long cid) {
        QueryWrapper wrapper = new QueryWrapper(  );
        wrapper.eq( "category_id" , cid );
        List<TbCategoryBrand> list = baseMapper.selectList( wrapper );
        List<Long>            collect = list.stream().map( list1 -> list1.getBrandId() ).collect( Collectors.toList() );
        return collect;
    }
}
