package com.leyou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyou.entity.TbSpecGroup;
import com.leyou.dao.TbSpecGroupMapper;
import com.leyou.entity.TbSpecParam;
import com.leyou.service.TbSpecGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leyou.service.TbSpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 * 规格参数的分组表，每个商品分类下有多个规格参数组 服务实现类
 * </p>
 *
 * @author qp
 * @since 2020-02-09
 */
@Service
public class TbSpecGroupServiceImpl extends ServiceImpl<TbSpecGroupMapper, TbSpecGroup> implements TbSpecGroupService {

    @Autowired
    private TbSpecParamService paramService;
    /**
     * 根据cid查询参数组
     * @param cid
     * @return
     */
    @Override
    public List<TbSpecGroup> queryGroupByCid(Long cid) {

        QueryWrapper wrapper =new QueryWrapper();
        wrapper.eq( "cid",cid );
        List<TbSpecGroup> list = baseMapper.selectList( wrapper );
        return list;
    }

    /**
     * 添加分组
     * @param tbSpecGroup
     * @return
     */
    @Override
    public int addTbSpecgroup(TbSpecGroup tbSpecGroup) {
        int i = baseMapper.insert( tbSpecGroup );
        return i;
    }

    /**
     * 修改分组
     * @param tbSpecGroup
     * @return
     */
    @Override
    public int updateTbSpecgroup(TbSpecGroup tbSpecGroup) {
        int update = baseMapper.updateById( tbSpecGroup );
        return update;
    }

    /**
     * 删除参数
     * @param id
     * @return
     */
    @Override
    public int deleteTbSpecgroup(Long id) {
        int i = baseMapper.deleteById( id );
        return i;
    }

    /**
     *根据cid 查询分类组信息
     * @param cid
     * @return
     */
    @Override
    public List<TbSpecGroup> queryGroupsWithParam(Long cid) {
        List<TbSpecGroup> groups = this.queryGroupByCid( cid );
        groups.forEach( group ->{
            List<TbSpecParam> params = this.paramService.queryTbSpecParamByGid( group.getId(),null,null,null );
            group.setParams( params );
        } );
        return  groups;
    }
}
