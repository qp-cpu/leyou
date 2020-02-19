package com.leyou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyou.entity.TbSpecParam;
import com.leyou.dao.TbSpecParamMapper;
import com.leyou.service.TbSpecParamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 规格参数组下的参数名 服务实现类
 * </p>
 *
 * @author qp
 * @since 2020-02-09
 */
@Service
public class TbSpecParamServiceImpl extends ServiceImpl<TbSpecParamMapper, TbSpecParam> implements TbSpecParamService {

    /**
     *
     *根据group_id查询规格参数
     *
     * @param cid
     * @param gid
     * @param generic
     * @param searching
     * @return
     */
    @Override
    public List<TbSpecParam> queryTbSpecParamByGid(Long gid,Long cid,Boolean generic,Boolean searching) {
        QueryWrapper wrapper = new QueryWrapper();
        if(gid != null) {
            wrapper.eq( "group_id",gid );
        }
        if(cid != null) {
            wrapper.eq( "cid",cid );
        }
        if(generic != null) {
            wrapper.eq( "generic",generic );
        }
        if(searching != null) {
            wrapper.eq( "searching",searching );
        }
        List list = baseMapper.selectList( wrapper );
        return list;
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
     * 修改参数
     * @param tbSpecParam
     * @return
     */
    @Override
    public int updatespecparem(TbSpecParam tbSpecParam) {
        int i = baseMapper.updateById( tbSpecParam );
        return i;
    }

    /**
     * 增加品牌参数
     * @param tbSpecParam
     * @return
     */
    @Override
    public int addspecparem(TbSpecParam tbSpecParam) {
        int i = baseMapper.insert( tbSpecParam );
        return i;
    }
}
