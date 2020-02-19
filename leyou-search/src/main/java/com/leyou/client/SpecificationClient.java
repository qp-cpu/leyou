package com.leyou.client;





import com.leyou.pojo.TbSpecParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@FeignClient( "leyou-item-service")
@RequestMapping("spec")
public interface SpecificationClient {

    /**
     *
     *根据规格参数查询
     * @param gid
     * @return
     */
    @GetMapping("params")
    public List<TbSpecParam> queryTbSpecParamByGid(@RequestParam(value = "gid", required = false) Long gid,
                                                   @RequestParam(value = "cid", required = false) Long cid,
                                                   @RequestParam(value = "generic", required = false) Boolean generic,
                                                   @RequestParam(value = "searching", required = false) Boolean searching);
}
