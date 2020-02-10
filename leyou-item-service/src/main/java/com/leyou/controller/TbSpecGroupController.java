package com.leyou.controller;


import com.leyou.entity.TbSpecGroup;
import com.leyou.service.TbSpecGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 规格参数的分组表，每个商品分类下有多个规格参数组 前端控制器
 * </p>
 *
 * @author qp
 * @since 2020-02-09
 */
@Controller
@RequestMapping("/spec")
public class TbSpecGroupController {

    @Autowired
    private TbSpecGroupService specGroupService;

    /**
     * 根据cid查询参数组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<TbSpecGroup>> queryGroupByCid(@PathVariable("cid") Long cid){
        List<TbSpecGroup> tbSpecGroups = this.specGroupService.queryGroupByCid( cid );
        if(CollectionUtils.isEmpty( tbSpecGroups ))
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( tbSpecGroups );
    }

    /**
     * 添加分组
     * @param tbSpecGroup
     * @return
     */
    @PostMapping("group")
    public ResponseEntity<Object> addTbSpecgroup(@RequestBody TbSpecGroup tbSpecGroup)
    {
        if(tbSpecGroup == null)
        {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
        int i = specGroupService.addTbSpecgroup( tbSpecGroup );
        return ResponseEntity.ok( i );
    }

    /**
     * 修改分组
     * @param tbSpecGroup
     * @return
     */
    @PutMapping("group")
    public ResponseEntity<Object> updateTbSpecgroup(@RequestBody TbSpecGroup tbSpecGroup)
    {
        if(tbSpecGroup == null)
        {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
        int i = specGroupService.updateTbSpecgroup( tbSpecGroup );
        return ResponseEntity.ok( i );
    }

    /**
     * 删除分组
     * @param id
     * @return
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity<Object> deleteTbSpecgroup(@PathVariable("id") Long id){

       int a = this.specGroupService.deleteTbSpecgroup(id);
       if(a<0)
       {
           return new ResponseEntity<>( HttpStatus.NOT_FOUND );
       }
       return ResponseEntity.ok().build();
    }
}
