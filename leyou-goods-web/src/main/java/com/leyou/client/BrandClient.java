package com.leyou.client;


import com.leyou.pojo.TbBrand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("leyou-item-service")
@RequestMapping("brand")
public interface BrandClient {

    /**
     * 根据brandid查询brand
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public TbBrand queryBrandById(@PathVariable("id") Long id);
}
