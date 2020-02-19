package com.leyou.controller;

import com.leyou.dto.SpuDto;
import com.leyou.entity.TbSpu;
import com.leyou.entity.TbSpuDetail;
import com.leyou.service.TbSpuDetailService;
import com.leyou.service.TbSpuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("goods")
public class GoodsController {

    /**
     * 新增商品
     */
    @Autowired
    private TbSpuService       spuService;

    @PostMapping("")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuDto spuDto) {

        if (spuDto == null) {
            return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
        }
//        新增spu
         this.spuService.SaveGoods( spuDto );
         return new ResponseEntity<>( HttpStatus.CREATED );
    }

    @PutMapping("")
    public ResponseEntity<Void> updtaeGoods(@RequestBody SpuDto spuDto)
    {
        if (spuDto == null) {
            return new ResponseEntity<Void>( HttpStatus.BAD_REQUEST );
        }
//        新增spu
        Integer integer = this.spuService.updateGoods( spuDto );
        if(integer ==null)
        {
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR );
        }
        return new ResponseEntity<>( HttpStatus.CREATED );
    }
}
