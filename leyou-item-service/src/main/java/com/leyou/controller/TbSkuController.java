package com.leyou.controller;


import com.leyou.entity.TbSku;
import com.leyou.service.TbSkuService;
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
 * sku表,该表表示具体的商品实体,如黑色的 64g的iphone 8 前端控制器
 * </p>
 *
 * @author qp
 * @since 2020-02-11
 */
@Controller
@RequestMapping("sku")
public class TbSkuController {

    @Autowired
    private TbSkuService tbSkuService;
    /**
     * 根据spuid查询sku集合
     * @param id
     * @return
     */
    @GetMapping("list")
    public ResponseEntity<List<TbSku>> QuerySkuBySpuId(@RequestParam(value = "id",required = true)Long id)
    {
        List<TbSku> skus = this.tbSkuService.QuerySkuBySpuId(id);
        if( CollectionUtils.isEmpty( skus ) )
        {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        }
        return ResponseEntity.ok( skus );
    }

    /**
     * 更具skuid 查询 sku
     * @param
     * @return
     */
    @GetMapping("{sid}")
    public ResponseEntity<TbSku> queryById(@PathVariable("sid") Long sid)
    {
       TbSku sku =  this.tbSkuService.queryById(sid);
       if (sku == null)
       {
           return ResponseEntity.badRequest().build();
       }
       return ResponseEntity.ok( sku );
    }

}
