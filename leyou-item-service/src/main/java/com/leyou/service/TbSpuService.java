package com.leyou.service;

import com.leyou.dto.SpuDto;
import com.leyou.entity.PageList;
import com.leyou.entity.TbSpu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * spu表，该表描述的是一个抽象性的商品，比如 iphone8 服务类
 * </p>
 *
 * @author qp
 * @since 2020-02-10
 */
public interface TbSpuService extends IService<TbSpu> {

    PageList<SpuDto> querySpuByPage(String key,Boolean saleable,Integer page,Integer rows);



    void SaveGoods(SpuDto spuDto);


    Integer updateGoods(SpuDto spuDto);
}
