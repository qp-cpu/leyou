package com.leyou.service;

import com.leyou.client.CartClient;
import com.leyou.interceptor.LoginInterceptor;
import com.leyou.pojo.Cart;
import com.leyou.pojo.TbSku;
import com.leyou.pojo.UserInfo;
import com.leyou.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {
    private static final String KEY_PREFIX = "user:cart:";
    @Autowired
    private LoginInterceptor interceptor;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CartClient cartClient;

    public void adCart(Cart cart) {

//        获取用户信息
        UserInfo userInfo = interceptor.getThreadLocal();

//        查询用户记录
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps( KEY_PREFIX + userInfo.getId() );

        String key = cart.getSkuId().toString();
        Integer num = cart.getNum();
//        判断商品是否在购物车中
       if (hashOperations.hasKey( key )){
//           在就更新
           String cartJson = hashOperations.get( key ).toString();
           cart  = JsonUtils.parse( cartJson,Cart.class );
           cart.setNum( cart.getNum() + num );

       }
       else {
//           不在就新增数据
           TbSku tbSku =  cartClient.queryById( cart.getSkuId() );
           cart.setUserId( userInfo.getId() );
           cart.setTitle( tbSku.getTitle() );
           cart.setOwnSpec( tbSku.getOwnSpec() );
           cart.setImage( StringUtils.isBlank( tbSku.getImages()) ? "" : StringUtils.split( tbSku.getImages(),","  )[0] );
           cart.setPrice( tbSku.getPrice() );
       }
          hashOperations.put( key , JsonUtils.serialize( cart ) );
    }

    public List<Cart> queryCarts() {
        UserInfo userInfo = interceptor.getThreadLocal();

        if(!this.redisTemplate.hasKey( KEY_PREFIX + userInfo.getId() ))
        {
            return null;
        }
//        获取用户的购物车记录
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps( KEY_PREFIX + userInfo.getId() );

        List<Object> cartJson = hashOperations.values();
        if(CollectionUtils.isEmpty( cartJson ))
        {
            return  null;
        }
//       把List《Object》集合转化为List《cart>集合
       return cartJson.stream().map( cart -> JsonUtils.parse(cart.toString(),Cart.class ) ).collect( Collectors.toList() );
    }

    public void updtaeBum(Cart cart) {
        UserInfo userInfo = interceptor.getThreadLocal();

       Integer num =cart.getNum();
        if(!this.redisTemplate.hasKey( KEY_PREFIX + userInfo.getId() ))
        {
            return ;
        }
        //        获取用户的购物车记录
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps( KEY_PREFIX + userInfo.getId() );

        String cartJson = hashOperations.get( cart.getSkuId().toString() ).toString();

        cart  = JsonUtils.parse( cartJson,Cart.class );
        cart.setNum( num );
        hashOperations.put( cart.getSkuId().toString(),JsonUtils.serialize( cart ) );
    }
}
