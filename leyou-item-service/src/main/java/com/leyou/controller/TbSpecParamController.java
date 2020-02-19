package com.leyou.controller;


import com.leyou.entity.TbSpecParam;
import com.leyou.service.TbSpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 规格参数组下的参数名 前端控制器
 * </p>
 *
 * @author qp
 * @since 2020-02-09
 */
@Controller
@RequestMapping("/spec")
public class TbSpecParamController {

    @Autowired
    private TbSpecParamService tbSpecParam;

    /**
     *
     *根据group_id查询规格参数
     * @param gid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<TbSpecParam>> queryTbSpecParamByGid(@RequestParam(value = "gid",required = false) Long gid,
                                                                   @RequestParam(value = "cid",required = false) Long cid,
                                                                   @RequestParam(value = "generic",required = false) Boolean generic,
                                                                   @RequestParam(value = "searching",required = false) Boolean searching)
    {
            List<TbSpecParam> params = this.tbSpecParam.queryTbSpecParamByGid( gid,cid,generic,searching );
            if (CollectionUtils.isEmpty( params )) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok( params );
    }

    /**
     * 删除分组
     * @param id
     * @return
     */
    @DeleteMapping("param/{id}")
    public ResponseEntity<Object> deleteTbSpecgroup(@PathVariable("id") Long id){

        int a = this.tbSpecParam.deleteTbSpecgroup(id);
        if(a<0)
        {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        }
        return ResponseEntity.ok().build();
    }

    /**
     * 修改参数
     * @param tbSpecParam
     * @return
     */
    @PutMapping("param")
    public ResponseEntity<Object> updatespecparem(@RequestBody TbSpecParam tbSpecParam)
    {
        if(tbSpecParam == null)
        {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
        int i = this.tbSpecParam.updatespecparem( tbSpecParam );
        return ResponseEntity.ok( i );
    }

    /**
     * 增加参数
     * @param tbSpecParam
     * @return
     */
    @PostMapping("param")
    public ResponseEntity<Object> addspecparem(@RequestBody TbSpecParam tbSpecParam)
    {
        if(tbSpecParam == null)
        {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
        int i = this.tbSpecParam.addspecparem( tbSpecParam );
        return ResponseEntity.ok( i );
    }
}
