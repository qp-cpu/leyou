package com.leyou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyou.entity.TbSku;
import com.leyou.entity.TbSpu;
import com.leyou.entity.TbSpuDetail;
import com.leyou.dao.TbSpuDetailMapper;
import com.leyou.service.TbSkuService;
import com.leyou.service.TbSpuDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author qp
 * @since 2020-02-10
 */
@Service
public class TbSpuDetailServiceImpl extends ServiceImpl<TbSpuDetailMapper, TbSpuDetail> implements TbSpuDetailService {

    @Autowired
    private TbSkuService skuService;
    /**
     * 增加商品detail
     * @param spuDetail
     */
    @Override
    public void addSpudetail(TbSpuDetail spuDetail) {
        baseMapper.insert( spuDetail );
    }

    /**
     * 根据spuid查询
     * @param spuid
     * @return
     */
    @Override
    public TbSpuDetail querySpudetailBySpuId(Long spuid) {
        TbSpuDetail tbSpuDetail = baseMapper.selectById( spuid );
        return tbSpuDetail;
    }


}
