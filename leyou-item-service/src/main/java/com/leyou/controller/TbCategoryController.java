package com.leyou.controller;


import com.leyou.entity.TbCategory;
import com.leyou.service.TbCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 商品类目表，类目和商品(spu)是一对多关系，类目与品牌是多对多关系 前端控制器
 * </p>
 *
 * @author qp
 * @since 2020-01-09
 */
@RestController
@RequestMapping("/category")
public class TbCategoryController {

    @Autowired
    private TbCategoryService tbCategoryService;

    /**
     * 更具父节点的id查询子节点
     *
     * @param pid
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<TbCategory>> queryTbCategoryByPid(@RequestParam(value = "pid", defaultValue = "0") Long pid) {

        if (pid == null || pid < 0) {
//                响应400参数不合法
//                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
        List<TbCategory> categories = tbCategoryService.queryTbCategoryByPid( pid );
        if (CollectionUtils.isEmpty( categories )) {
//                404：资源服务器未找到
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        }
//            200 查询成功
        return ResponseEntity.ok( categories );
    }

    /**
     * 分类管理插入分类
     *
     * @param tbCategory
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<TbCategory> addCategory(@RequestBody TbCategory tbCategory) {

        if (tbCategory == null) {
         return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }

        boolean b = tbCategoryService.addCategory( tbCategory );
        if (b == true) {
            return new ResponseEntity<>( HttpStatus.OK );
        } else {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        }
    }

    /**
     * 根据id删除分类管理分类商品
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public ResponseEntity<TbCategory> deleteByTbCategoryId(@RequestParam(name = "id",required = true) Long id)
    {
     boolean a = tbCategoryService.deleteByTbCategoryId(id);
     if (a == true)
     {
         return new ResponseEntity<>( HttpStatus.OK );
     }
     else {
         return new ResponseEntity<>( HttpStatus.NOT_FOUND );
     }
    }
}
