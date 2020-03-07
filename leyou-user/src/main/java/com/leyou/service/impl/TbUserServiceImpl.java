package com.leyou.service.impl;


import ch.qos.logback.core.util.TimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.leyou.entity.TbUser;
import com.leyou.dao.TbUserMapper;
import com.leyou.service.TbUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leyou.utils.CodecUtils;
import com.leyou.utils.NumberUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author qp
 * @since 2020-02-28
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    private static  final  String  KEY_PREFIX="user:verity";

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 判断用户可不可用
     * @param data
     * @param type
     * @return
     */
    @Override
    public Boolean checkUser(String data,Integer type) {

        QueryWrapper<TbUser> wrapper = new QueryWrapper<>();

        if(type == 1)
        {
            wrapper.eq( "username" ,data);
        }
        else if(type == 2){
            wrapper.eq( "phone",data );
        }
        else {
            return null;
        }
        return  this.baseMapper.selectCount( wrapper ) == 0;

    }

    /**
     * 发送短信
     * @param phone
     * @return
     */
    @Override
    public void sendVerifyCode(String phone) {
//        判断phone是否为空
        if(StringUtils.isBlank( phone ))
        {
            return;
        }
//        生成验证码
        String code = NumberUtils.generateCode( 6 );
//        发送消息到rabbitmq
        Map<String,String> map = new HashMap<>(  );
        map.put( "phone", phone);
        map.put( "code",code );
        this.amqpTemplate.convertAndSend( "leyou.sms.exchange","verifycode_sms",map);

//        把验证码保存到Redis
      this.redisTemplate.opsForValue().set( KEY_PREFIX+phone,code,5,TimeUnit.MINUTES );
    }

    /**
     * 用户注册
     * @param user
     */
    @Override
    public void register(TbUser user) {
        if (user == null)
        {
            return;
        }

        String rediscode = this.redisTemplate.opsForValue().get( KEY_PREFIX + user.getPhone() );
//        校验验证码
        if (!StringUtils.equals( user.getCode(),rediscode ))
        {
            return;
        }
//        生成言
        String salt = CodecUtils.generateSalt();
        user.setSalt( salt );
//        加言加密
        user.setPassword(CodecUtils.md5Hex( user.getPassword(),salt ));
//        新增用户
         user.setId( null );
         user.setCreated( new Date(  ) );
         this.baseMapper.insert( user );
//         删除Redis缓存的验证码
         this.redisTemplate.delete( KEY_PREFIX + user.getPhone() );
    }

    /**
     * 查询用户根据用户名和密码
     * @param username
     * @param password
     * @return
     */
    @Override
    public TbUser queryUser(String username,String password) {

        QueryWrapper<TbUser> wrapper = new QueryWrapper<>();
        wrapper.eq( "username",username );
        TbUser user = this.baseMapper.selectOne( wrapper );

        //判断uesr是否为空
        if(user == null)
        {
            return null;
        }

//        获取言对用户输入密码加言加密，在与数据库密码比较

         password = CodecUtils.md5Hex( password,user.getSalt() );


        if(StringUtils.equals( password, user.getPassword()))
        {
            return  user;
        }
        return  null;
    }
}
