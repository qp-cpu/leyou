package com.leyou.controller;


import com.leyou.dto.SpuDto;
import com.leyou.entity.PageList;
import com.leyou.entity.TbSpecParam;
import com.leyou.entity.TbSpu;
import com.leyou.service.TbSpuService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * spu表，该表描述的是一个抽象性的商品，比如 iphone8 前端控制器
 * </p>
 *
 * @author qp
 * @since 2020-02-10
 */
@Controller
@RequestMapping("/spu")
public class TbSpuController {

    @Autowired
    private TbSpuService spuService;


    @GetMapping("page")
    public ResponseEntity<PageList<SpuDto>> querySpuByPage(@RequestParam(value = "key",required = false) String key,
                                                           @RequestParam(value = "saleable",defaultValue = "true") Boolean saleable,
                                                           @RequestParam(value = "page",defaultValue = "1") Integer page,
                                                           @RequestParam(value = "rows",defaultValue = "5") Integer rows
                                                           )
    {
        PageList<SpuDto> spuDtos  = this.spuService.querySpuByPage(key,saleable,page,rows);

        ArrayList<Object> objects = new ArrayList<>();
        if(spuDtos==null || CollectionUtils.isEmpty( spuDtos.getItems() ))
        {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        }
        return ResponseEntity.ok( spuDtos );
    }

    /**
     *
     * 根据spu id查询 spu
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<TbSpu> querySpuByid(@PathVariable("id") Long id)
    {
        TbSpu spu = this.spuService.querySpuByid(id);
        if(spu == null)
        {
            return ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok( spu );
    }

}
