package com.leyou.client;

import com.leyou.pojo.TbSku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("leyou-item-service")
public interface CartClient {
    /**
     * 更具skuid 查询 sku
     * @param
     * @return
     */
    @GetMapping("sku/{sid}")
    public TbSku queryById(@PathVariable Long sid);
}
