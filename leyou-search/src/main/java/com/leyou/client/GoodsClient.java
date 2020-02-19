package com.leyou.client;


import com.leyou.pojo.PageList;
import com.leyou.pojo.SpuDto;
import com.leyou.pojo.TbSku;
import com.leyou.pojo.TbSpuDetail;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("leyou-item-service")
public interface GoodsClient {

    /**
     * 根据spuid查询TbSpuDetail
     * @param spuid
     * @return
     */
    @GetMapping("spu/detail/{spuid}")
    public TbSpuDetail querySpudetailBySpuId(@PathVariable("spuid") Long spuid);

    /**
     * 根据条件分页查询spu
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("spu/page")
    public PageList<SpuDto> querySpuByPage(@RequestParam(value = "key", required = false) String key,
                                           @RequestParam(value = "saleable", defaultValue = "true") Boolean saleable,
                                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                                           @RequestParam(value = "rows", defaultValue = "5") Integer rows
    );
    /**
     * 根据spuid查询slu集合
     * @param id
     * @return
     */
    @GetMapping("sku/list")
    public List<TbSku> QuerySkuBySpuId(@RequestParam(value = "id",required = true)Long id);

}
