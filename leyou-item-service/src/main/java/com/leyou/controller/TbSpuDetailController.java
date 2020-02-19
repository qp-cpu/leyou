package com.leyou.controller;


import com.leyou.entity.TbSku;
import com.leyou.entity.TbSpu;
import com.leyou.entity.TbSpuDetail;
import com.leyou.service.TbSkuService;
import com.leyou.service.TbSpuDetailService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author qp
 * @since 2020-02-10
 */
@Controller
@RequestMapping("/spu")
public class TbSpuDetailController {

    @Autowired
    private TbSpuDetailService spuDetailService;


    /**
     * 更具spuid查询TbSpuDetail
     * @param spuid
     * @return
     */
    @GetMapping("detail/{spuid}")
    public ResponseEntity<TbSpuDetail> querySpudetailBySpuId(@PathVariable("spuid")Long spuid)
    {
       TbSpuDetail spuDetails = this.spuDetailService.querySpudetailBySpuId(spuid);
       if(spuDetails == null)
       {
           return new ResponseEntity<>( HttpStatus.NOT_FOUND );
       }
       return ResponseEntity.ok( spuDetails );
    }


}
