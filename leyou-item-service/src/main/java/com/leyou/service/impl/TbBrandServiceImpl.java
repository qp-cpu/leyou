package com.leyou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyou.entity.PageList;
import com.leyou.entity.TbBrand;
import com.leyou.dao.TbBrandMapper;
import com.leyou.service.TbBrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leyou.service.TbCategoryBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 品牌表，一个品牌下有多个商品（spu），一对多关系 服务实现类
 * </p>
 *
 * @author qp
 * @since 2020-01-10
 */
@Service
public class TbBrandServiceImpl extends ServiceImpl<TbBrandMapper, TbBrand> implements TbBrandService {

    @Autowired
    private TbBrandMapper tbBrandMapper;

    @Autowired
    private TbCategoryBrandService tbCategoryBrandService;

    /**
     * 根据查询条件分页查询品牌信息
     *
     * @param key    //搜索条件
     * @param page   //当前页
     * @param rows   ,//每页大小
     * @param sortBy //按照排序
     * @param desc   //是否降序
     * @return
     */

    @Override
    public PageList<TbBrand> queryBrandssByPage(String key,Integer page,Integer rows,String sortBy,Boolean desc) {

//        查询分页record ,没有key的情况
        if (StringUtils.isEmpty( key )) {
            IPage                 page1   = new Page( page,rows );
            QueryWrapper<TbBrand> wrapper = new QueryWrapper<>();
            if (desc == true) {
                wrapper.orderByDesc( sortBy );
            } else {
                wrapper.orderByAsc( sortBy );
            }
            IPage             iPage    = baseMapper.selectPage( page1,wrapper );
            PageList<TbBrand> pageList = new PageList<>();
            pageList.setItemsLength( (int) iPage.getTotal() );
            pageList.setItems( iPage.getRecords() );
            pageList.setPageStart( page );
            pageList.setPageStop( page + rows - 1 );
            return pageList;
        }
        //        查询分页record ,有key的情况
        else {
            IPage                 page1   = new Page( page,rows );
            QueryWrapper<TbBrand> wrapper = new QueryWrapper<>();
            if (desc == true) {
                wrapper.orderByDesc( sortBy );
            } else {
                wrapper.orderByAsc( sortBy );
            }
            wrapper.like( "name",key );
            IPage             iPage    = baseMapper.selectPage( page1,wrapper );
            PageList<TbBrand> pageList = new PageList<>();
            pageList.setItemsLength( (int) iPage.getTotal() );
            pageList.setItems( iPage.getRecords() );
            pageList.setPageStart( page );
            pageList.setPageStop( page + rows - 1 );
            return pageList;
        }
    }

    /**
     * 根据id删除分类管理分类商品
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(Long id) {

        int i = baseMapper.deleteById( id );
        if (i == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 新增商品
     *
     * @param tbBrand
     * @return
     */
    @Transactional
    @Override
    public boolean addBrand(TbBrand tbBrand) {
        int insert = baseMapper.insert( tbBrand );
        if (insert > 0) {
            for (Long cid:tbBrand.getCategories()) {
//                添加商品，品牌中间表
             tbCategoryBrandService.addCategoryBrand(cid,tbBrand.getId());
            }
            return true;
        } else {
            return false;
        }
    }
}
