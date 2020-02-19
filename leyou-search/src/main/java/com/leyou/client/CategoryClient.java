package com.leyou.client;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("leyou-item-service")
@RequestMapping("category")
public interface CategoryClient {

    /**
     * 根据catogriesid 查询 name
     * @param ids
     * @return
     */
    @GetMapping("")
    public List<String> queryNameByIds(@RequestParam("ids") List<Long> ids);
}
