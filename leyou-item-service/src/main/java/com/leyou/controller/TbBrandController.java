package com.leyou.controller;


import com.leyou.entity.PageList;
import com.leyou.entity.TbBrand;
import com.leyou.service.TbBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 品牌表，一个品牌下有多个商品（spu），一对多关系 前端控制器
 * </p>
 *
 * @author qp
 * @since 2020-01-10
 */
@RestController
@RequestMapping("/brand")
public class TbBrandController {

    @Autowired
    private TbBrandService tbBrandService;

    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key //搜索条件
     * @param page //当前页
     * @param rows ,//每页大小
     * @param sortBy //按照排序
     * @param desc //是否降序
     * @return
     */

    @GetMapping("/page")
    public ResponseEntity<PageList<TbBrand>> queryBrandsByPage(
            @RequestParam(name = "key", required = false) String key,
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "rows", defaultValue = "5") Integer rows,
            @RequestParam(name = "sortBy", required = false) String sortBy,
            @RequestParam(name = "desc", required = false) Boolean desc) {
         PageList<TbBrand> result = tbBrandService.queryBrandssByPage( key, page, rows, sortBy, desc );
        if (result == null || CollectionUtils.isEmpty( result.getItems() )) {
            return new ResponseEntity<>( HttpStatus.NOT_FOUND );
        }
        return ResponseEntity.ok( result );
    }

    /**
     * 删除商品BY id
     * @param id
     * @return
     */
    @DeleteMapping("")
    public ResponseEntity<TbBrand> deleteById(@RequestParam(name = "id",required = false) Long id)
    {
        boolean b = tbBrandService.deleteById( id );
        if (b==true)
        {
            return  ResponseEntity.ok().build();
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 新增商品
     * @param tbBrand
     * @return
     */
    @PostMapping("")
    public ResponseEntity<TbBrand> addBrand(@RequestBody TbBrand tbBrand)
    {
        if (tbBrand == null)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

       boolean b = tbBrandService.addBrand(tbBrand);
        if (b==true)
        {
            return new  ResponseEntity<>( HttpStatus.CREATED );
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
