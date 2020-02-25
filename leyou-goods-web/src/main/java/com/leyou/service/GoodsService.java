package com.leyou.service;

import com.leyou.client.BrandClient;
import com.leyou.client.CategoryClient;
import com.leyou.client.GoodsClient;
import com.leyou.client.SpecificationClient;
import com.leyou.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GoodsService {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;


    public Map<String,Object> loadData(Long spuid)
    {
        Map<String,Object> map = new HashMap<>(  );
//        根据spuId查询spu
        TbSpu tbSpu = this.goodsClient.querySpuByid( spuid );

//        查询spudetail
        TbSpuDetail tbSpuDetail = this.goodsClient.querySpudetailBySpuId( spuid );

//        查询分类,Map<String,Object>
        List<Long>   cids   = Arrays.asList( tbSpu.getCid1(),tbSpu.getCid2(),tbSpu.getCid3() );
        List<String> names = this.categoryClient.queryNameByIds( cids );
//          初始化一个分类的map
        List<Map<String,Object>> categories =new ArrayList<>(  );
        for (int i = 0; i < cids.size(); i++) {
            Map<String,Object> map1= new HashMap<>(  );
            map1.put( "id",cids.get(i) );
            map1.put( "name",names.get( i ) );
            categories.add( map1 );
        }
//        查询品牌
        TbBrand tbBrand = this.brandClient.queryBrandById( tbSpu.getBrandId() );
        
//        查询skus
        List<TbSku> tbSkus = this.goodsClient.QuerySkuBySpuId( spuid );

//        查询规格参数组
        List<TbSpecGroup> tbSpecGroups = this.specificationClient.queryGroupsWithParam( tbSpu.getCid3() );

//        查询特殊的规格参数
        List<TbSpecParam> tbSpecParams = this.specificationClient.queryTbSpecParamByGid( null,tbSpu.getCid3(),false,null );
//       初始化特殊规格参数的map
        Map<Long,String> paramMap = new HashMap<>(  );
        tbSpecParams.forEach( param ->{
            paramMap.put( param.getId(),param.getName() );
        } );
        map.put( "spu",tbSpu );
        map.put( "spuDetail",tbSpuDetail );
        map.put( "categories",categories );
        map.put( "brand",tbBrand );
        map.put( "skus",tbSkus );
        map.put( "groups",tbSpecGroups );
        map.put( "paramMap",paramMap );


        return map;
    }

}
