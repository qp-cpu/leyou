package com.leyou.test;


import com.leyou.client.GoodsClient;

import com.leyou.pojo.Goods;
import com.leyou.pojo.PageList;
import com.leyou.pojo.SpuDto;
import com.leyou.repostory.GoodsRepository;
import com.leyou.serice.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticSearchTest {

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private SearchService service;
    @Autowired
    private GoodsClient   goodsClient;


    @Test
    public void test(){
//        创建索引库
       this.template.createIndex( Goods.class );
//       创建映射
       this.template.putMapping( Goods.class );

       Integer page =1;
       Integer rows = 100 ;
       do{
//     分页获取LIst goods集合
           PageList<SpuDto> result = this.goodsClient.querySpuByPage( null,null,page,rows );
           List<SpuDto>     items  = result.getItems();
           List<Goods> goodsList = items.stream().map( spuDto -> {
               try {
                   return this.service.buildGoods( spuDto );
               } catch (IOException e) {
                   e.printStackTrace();
               }
               return null;
           } ).collect( Collectors.toList() );

//      执行新增数据
           this.goodsRepository.saveAll( goodsList );
           rows = items.size();
           page ++ ;
       }
       while (rows == 100);
    }

}
