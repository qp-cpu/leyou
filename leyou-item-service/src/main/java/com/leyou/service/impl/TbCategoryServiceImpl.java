package com.leyou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyou.entity.TbCategory;
import com.leyou.dao.TbCategoryMapper;
import com.leyou.service.TbCategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品类目表，类目和商品(spu)是一对多关系，类目与品牌是多对多关系 服务实现类
 * </p>
 *
 * @author qp
 * @since 2020-01-09
 */
@Service
public class TbCategoryServiceImpl extends ServiceImpl<TbCategoryMapper, TbCategory> implements TbCategoryService {


    /**
     * 根据父节点查询子节点
     *
     * @param pid
     * @return
     */
    @Override
    public List<TbCategory> queryTbCategoryByPid(Long pid) {
//        return (List<TbCategory>) baseMapper.selectById(pid);
        QueryWrapper<TbCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq( "parent_id",pid );
        return baseMapper.selectList( queryWrapper );
    }

    /**
     * 分类管理插入分类
     *
     * @param tbCategory
     * @return
     */
    @Override
    public boolean addCategory(TbCategory tbCategory) {
        int i = baseMapper.insert( tbCategory );
        if (i>0)
        {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 删除商品BY id
     * @param id
     * @return
     */
    @Override
    public boolean deleteByTbCategoryId(Long id) {

        int i = baseMapper.deleteById(id);
        if(i>0)
        {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 根据cid分类名称
     * @param list
     * @return
     */
    @Override
    public List<String> selectBycid(ArrayList<Long> list) {
        List<TbCategory> categories = baseMapper.selectBatchIds( list );
        List<String>     collect    = categories.stream().map( categorie -> categorie.getName() ).collect( Collectors.toList() );
        return collect;
    }

    /**
     * 通过id 获取name
     * @param ids
     */
    @Override
    public List<String> queryTbCategoryByids(List<Long> ids) {

        List<TbCategory> categories = baseMapper.selectBatchIds( ids );
        List<String>     collect    = categories.stream().map( categorie -> categorie.getName() ).collect( Collectors.toList() );

        return  collect;
    }
}
