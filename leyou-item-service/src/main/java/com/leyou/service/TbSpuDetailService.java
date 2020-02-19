package com.leyou.service;

import com.leyou.entity.TbSku;
import com.leyou.entity.TbSpu;
import com.leyou.entity.TbSpuDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author qp
 * @since 2020-02-10
 */
public interface TbSpuDetailService extends IService<TbSpuDetail> {

    void addSpudetail(TbSpuDetail spuDetail);

    TbSpuDetail querySpudetailBySpuId(Long spuid);


}
