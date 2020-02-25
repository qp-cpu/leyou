package com.leyou.serice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.leyou.client.BrandClient;
import com.leyou.client.CategoryClient;
import com.leyou.client.GoodsClient;
import com.leyou.client.SpecificationClient;

import com.leyou.pojo.*;
import com.leyou.repostory.GoodsRepository;
import com.sun.deploy.util.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SearchService {

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;

    @Autowired
    private GoodsRepository goodsRepository;

    private static final ObjectMapper MAPPER =   new ObjectMapper(  );

    public Goods buildGoods(SpuDto spu) throws IOException {
        Goods goods = new Goods();
        //根据分类的id查询分类的名称
        List<String> names = this.categoryClient.queryNameByIds( Arrays.asList( spu.getCid1(),spu.getCid2(),spu.getCid3() ) );

        //根据品牌id查询品牌
        TbBrand brand = this.brandClient.queryBrandById( spu.getBrandId() );

        //根据spuid查询所有的sku
        List<TbSku>              skus       = this.goodsClient.QuerySkuBySpuId( spu.getId() );
        List<Long>               prices     = new ArrayList<>(  );
        List<Map<String,Object>> skuMapList = new ArrayList<>(  );
//        List<Long>  prices = skus.stream().map( sku -> sku.getPrice() ).collect( Collectors.toList() );
       skus.forEach( sku ->{
           prices.add( sku.getPrice() );
           Map<String,Object> map = new HashMap<>(  );
           map.put( "id" ,sku.getId() );
           map.put( "title",sku.getTitle() );
           map.put( "price" ,sku.getPrice());
           //获取sku中的图片，可能是多张
           map.put( "image" ,org.apache.commons.lang3.StringUtils.isBlank( sku.getImages() ) ? "" : StringUtils.splitString( sku.getImages(),"," )[0]);
           skuMapList.add( map );
       } );

       //根据spu中的cid3查询所有的搜索规格参数
        List<TbSpecParam> params = this.specificationClient.queryTbSpecParamByGid( null,spu.getCid3(),null,true );

        //根据spuid查询spudetail
        TbSpuDetail spuDetail = this.goodsClient.querySpudetailBySpuId( spu.getId() );
        //把通用的规格参数值反序列化
        Map<String,Object> genericSpecMap  = MAPPER.readValue( spuDetail.getGenericSpec() , new TypeReference<Map<String,Object>>(){});
        //把特殊的规格参数值反序列
        Map<String,List<Object>> specialSpecMap  = MAPPER.readValue( spuDetail.getSpecialSpec() , new TypeReference<Map<String,List<Object>>>(){});

        Map<String,Object> spec = new HashMap<>(  );
        params.forEach( param ->{
            //判断规格参数的类型，是否是通用的规格参数
            if(param.getGeneric())
            {
                //如果是通用类型的参数，从genericSpecmap获取规格参数值
                String value = genericSpecMap.get( param.getId().toString() ).toString();
                //判断是否是数值类型，如果是数值类型则可以返回一个区间
                if(param.getNumeric())
                {
                    value = chooseSegment( value,param );
                }
                spec.put( param.getName() ,value );
            }
            else {
//                如果是特殊的规格参数，就直接在specialSpecMap获取值就可以了
                List<Object> value = specialSpecMap.get( param.getId().toString() );
                spec.put( param.getName(),value );
            }
        } );

        goods.setId( spu.getId() );
        goods.setCid1( spu.getCid1() );
        goods.setCid2( spu.getCid2() );
        goods.setCid3( spu.getCid3() );
        goods.setBrandId( spu.getBrandId() );
        goods.setCreateTime( spu.getCreateTime() );
        goods.setSubTitle( spu.getSubTitle() );
        //拼接all字段，需要分类名称以及品牌名称
        goods.setAll( spu.getTitle() + " " + StringUtils.join(names," ") + " "  + brand.getName() );
        //获取spu下的所有sku的价格
        goods.setPrice( prices );
        //获取spu下的所有sku，并转化为json字符串保存
        goods.setSkus( MAPPER.writeValueAsString( skuMapList ) );
        //获取所有查询的规格参数{name : value}
        goods.setSpecs( spec );
        return goods;
    }
    private String chooseSegment(String value, TbSpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if(segs.length == 2){
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if(val >= begin && val < end){
                if(segs.length == 1){
                    result = segs[0] + p.getUnit() + "以上";
                }else if(begin == 0){
                    result = segs[1] + p.getUnit() + "以下";
                }else{
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    public SearchResult<Goods> search(SearchRequest request) {

//        自定义查询构造器
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
//        添加查询条件
//         QueryBuilder baseicQuery = QueryBuilders.matchQuery( "all",request.getKey() ).operator( Operator.AND );
        BoolQueryBuilder baseicQuery = buildBoolQueryBuilder(request);
        if(!org.apache.commons.lang3.StringUtils.isBlank( request.getKey() ))
        {
            builder.withQuery( baseicQuery );
        }
//        添加分页
         builder.withPageable( PageRequest.of( request.getPage()-1,request.getSize() ) );
//        添加结果集过滤
        builder.withSourceFilter( new FetchSourceFilter( new String[]{"id","skus","subTitle"},null ) );

//        添加分类和品牌的聚合
        String categoryAggName = "categories";
        String brandAggName = "brands";
        builder.addAggregation( AggregationBuilders.terms( categoryAggName ).field( "cid3" ) );
        builder.addAggregation( AggregationBuilders.terms( brandAggName ).field( "brandId" ) );

//        执行查询,获取结果集
        AggregatedPage<Goods> goodsPage = (AggregatedPage<Goods>)this.goodsRepository.search(builder.build());

//        获取分类聚合结果集并解析
        List<Map<String,Object>> categories = getCategoryAggResult( ((AggregatedPage<Goods>) goodsPage).getAggregation(categoryAggName));
//        获取品牌聚合结果集并解析
        List<TbBrand> brands = getBrandAggResult( ((AggregatedPage<Goods>) goodsPage).getAggregation(brandAggName));

//        判断是否是一个分类，只有一个分类时才能规格参数的聚合
        List<Map<String,Object>> specs = null ;
        if (!CollectionUtils.isEmpty( categories ) && categories.size() ==1)
        {
//            对规格参数进行聚合
         specs =  getParamAggResult( (Long) categories.get( 0 ).get( "id" ),baseicQuery);

        }

         return new SearchResult<Goods>(goodsPage.getTotalElements(),goodsPage.getTotalPages(),goodsPage.getContent(),categories,brands,specs);
    }

    /**
     * 构建boolean查询
     * @param request
     * @return
     */
    private BoolQueryBuilder buildBoolQueryBuilder(SearchRequest request) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
       //给Boolean查询添加基本查询条件
        builder.must( QueryBuilders.matchQuery( "all",request.getKey() ).operator( Operator.AND ) );
//        添加过滤条件
        Map<String, Object> filter = request.getFilter();
        for (Map.Entry<String, Object> entry : filter.entrySet()) {
            String key = entry.getKey();
            if(org.apache.commons.lang.StringUtils.equals( "品牌",key ))
            {
                key = "brandId";
            }else if(org.apache.commons.lang.StringUtils.equals( "分类",key )){
                key = "cid3";
            }
            else {
                key = "specs." + key + ".keyword";
            }
            builder.filter( QueryBuilders.termQuery( key , entry.getValue()) );
        }


        return builder;
    }

    /**
     * 根据查询条件聚合规格参数
     * @param id
     * @param baseicQuery
     * @return
     */
    private List<Map<String, Object>> getParamAggResult(Long id,QueryBuilder baseicQuery) {
        //自定义查询对象构建
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加基本查询条件
        queryBuilder.withQuery(baseicQuery);

        //查询要聚合的规格参数
        List<TbSpecParam> params = this.specificationClient.queryTbSpecParamByGid(null, id, null, true);

        //添加规格参数的聚合
        params.forEach(param ->{
            queryBuilder.addAggregation(AggregationBuilders.terms(param.getName()).field("specs."+ param.getName()+".keyword"));
        });

        //添加结果集过滤
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{},null));

        //执行聚合查询,获取聚合结果集
        AggregatedPage<Goods> goodsPage = (AggregatedPage<Goods>)this.goodsRepository.search(queryBuilder.build());

        List<Map<String,Object>> specs = new ArrayList<>();
        //解析聚合结果集,    key-聚合名称（规格参数名） value-聚合对象
        Map<String,Aggregation> aggregationMap = goodsPage.getAggregations().asMap();
        for (Map.Entry<String,Aggregation> entry : aggregationMap.entrySet()){
            //初始化一个map  {k：规格参数名 options：聚合的规格参数值}
            Map<String,Object> map = new HashMap<>();
            map.put("k",entry.getKey());
            //初始化一个options集合，收集桶中的key
            List<String> options = new ArrayList<>();
            //获取聚合
            StringTerms terms = (StringTerms)entry.getValue();
            //获取桶集合
            terms.getBuckets().forEach(bucket -> {
                options.add(bucket.getKeyAsString());
            });
            map.put("options",options);
            specs.add(map);

        }
        return specs;
    }

    /**
     * 品牌聚合结果集解析
     * @param aggregation
     * @return
     */
    private List<TbBrand> getBrandAggResult(Aggregation aggregation) {
        LongTerms terms = (LongTerms) aggregation;

//        获取聚合中的桶
        return terms.getBuckets().stream().map( bucket -> {
            return this.brandClient.queryBrandById( bucket.getKeyAsNumber().longValue() );
        } ).collect( Collectors.toList() );
    }
    /**
     *  分类聚合结果集解析
     * @param aggregation
     * @return
     */
    private List<Map<String, Object>> getCategoryAggResult(Aggregation aggregation) {
        LongTerms terms = (LongTerms) aggregation;
        return terms.getBuckets().stream().map( bucket -> {
            Map<String, Object> map  = new HashMap<>();
            long                id   = bucket.getKeyAsNumber().longValue();
            List<String>        list = this.categoryClient.queryNameByIds( Arrays.asList( id ) );
            map.put( "id",id );
            map.put( "name",list.get( 0 ) );
            return map;
        } ).collect( Collectors.toList() );
    }
}
