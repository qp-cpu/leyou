package com.leyou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leyou.dto.SpuDto;
import com.leyou.entity.*;
import com.leyou.dao.TbSpuMapper;
import com.leyou.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * spu表，该表描述的是一个抽象性的商品，比如 iphone8 服务实现类
 * </p>
 *
 * @author qp
 * @since 2020-02-10
 */
@Service
public class TbSpuServiceImpl extends ServiceImpl<TbSpuMapper, TbSpu> implements TbSpuService {

      @Autowired
      private TbBrandService tbBrandService;
      @Autowired
      private TbCategoryService tbCategoryService;
      @Autowired
      private TbSpuDetailService spuDetailService;
      @Autowired
      private TbStockService stockService;
      @Autowired
      private TbSkuService tbSkuService;
    /**
     * 根据条件分页查询spu集
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @Override
    public PageList<SpuDto> querySpuByPage(String key,Boolean saleable,Integer page,Integer rows) {
        IPage                 page1   = new Page( page,rows );
        QueryWrapper<TbSpu> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty( key ))
        {
            wrapper.like( "title",key );
        }
        if(!saleable == false)
        {
            wrapper.eq( "saleable",saleable );
        }

        IPage             iPage    = baseMapper.selectPage( page1,wrapper );
//        把spu转化成spudto
        List<SpuDto> collect = (List<SpuDto>) iPage.getRecords().stream().map( spu -> {
            SpuDto spuDto = new SpuDto();
            BeanUtils.copyProperties( spu,spuDto );
//            查询品牌名称
            TbBrand tbBrand = this.tbBrandService.selectByBid( spuDto.getBrandId() );
            spuDto.setBname( tbBrand.getName() );
//            查询分类名称
            ArrayList<Long> list = new ArrayList<>();
            list.add( spuDto.getCid1() );
            list.add( spuDto.getCid2() );
            list.add( spuDto.getCid3() );
            List<String> categories = this.tbCategoryService.selectBycid( list );
            String       str        = categories.get( 0 ) + "-" + categories.get( 1 ) + "-" + categories.get( 2 );
            spuDto.setCname( str );
            return spuDto;
        } ).collect( Collectors.toList() );
        PageList<SpuDto> pageList = new PageList<>();
        pageList.setItemsLength( (int) iPage.getTotal() );
        pageList.setItems( collect );
        pageList.setPageStart( page );
        pageList.setPageStop( page + rows - 1 );
        return pageList;
    }


    /**
     * 新增商品
     * @param spuDto
     */
    @Override
    @Transactional
    public void SaveGoods(SpuDto spuDto) {

//        先新增商品
        spuDto.setId( null );
        spuDto.setSaleable( true );
        spuDto.setValid( true );
        spuDto.setCreateTime(new Date(  ));
        spuDto.setLastUpdateTime( spuDto.getCreateTime() );
        this.baseMapper.insert( spuDto );
//   增加spudetail
        TbSpuDetail spuDetail = spuDto.getSpuDetail();
        spuDetail.setSpuId( spuDto.getId() );
        this.spuDetailService.addSpudetail( spuDetail );
        saveSkuandStock( spuDto );

    }

    /**
     * 新增sku 新增stock
     * @param spuDto
     */
    private void saveSkuandStock(SpuDto spuDto) {
        spuDto.getSkus().forEach( sku -> {
 //        新增sku
            sku.setId( null );
            sku.setSpuId( spuDto.getId() );
            sku.setCreateTime( new Date(  ) );
            sku.setLastUpdateTime( sku.getCreateTime() );
            this.tbSkuService.addsku(sku);
 //           新增库存
            TbStock tbStock = new TbStock();
            tbStock.setSkuId( sku.getId() );
            tbStock.setStock( sku.getStock());
            this.stockService.addstock(tbStock);
        } );
    }

    /**
     * 更新商品信息
     * @param spuDto
     */
    @Override
    @Transactional
    public Integer updateGoods(SpuDto spuDto) {
//        查询skuid
        List<Long> skuids = this.tbSkuService.qureySkuidBySpuid( spuDto.getId() );
//        删除stock
        this.stockService.deleteBySkuids(skuids);
//        删除sku
         this.tbSkuService.deleteBySkuids(skuids);
//        新增sku 新增stock
        this.saveSkuandStock( spuDto );
//        更新spu
        TbSpu tbSpu = new TbSpu();
        BeanUtils.copyProperties( spuDto,tbSpu );
        tbSpu.setCreateTime( null );
        tbSpu.setLastUpdateTime( new Date(  ) );
        tbSpu.setValid( null );
        tbSpu.setSaleable( null );
        baseMapper.updateById(  tbSpu);
//        更新spu detail
        TbSpuDetail spuDetail = spuDto.getSpuDetail();
        spuDetail.setSpuId( spuDto.getId() );
        this.spuDetailService.updateById( spuDetail );
        return 1;
        }

    /**
     *
     * 根据spu id查询 spu
     * @param id
     * @return
     */
    @Override
    public TbSpu querySpuByid(Long id) {
        TbSpu tbSpu = baseMapper.selectById( id );
        return tbSpu;
    }


}
