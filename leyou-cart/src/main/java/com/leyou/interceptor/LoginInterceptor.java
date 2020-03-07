package com.leyou.interceptor;

import com.leyou.config.JwtProperties;
import com.leyou.pojo.UserInfo;
import com.leyou.utils.CookieUtils;
import com.leyou.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@EnableConfigurationProperties(JwtProperties.class)
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private static  final ThreadLocal<UserInfo> THREAD_LOCAL = new ThreadLocal<>();

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception {

//        获取cookie中的token
        String token = CookieUtils.getCookieValue( request,this.jwtProperties.getCookieName() );

//        解析token获取用户信息
        UserInfo userInfo = JwtUtils.getInfoFromToken( token,this.jwtProperties.getPublicKey() );

        if (userInfo == null)
        {
            return false;
        }
//        把userinfo放入线程局部变量
        THREAD_LOCAL.set( userInfo );

        return true;

    }

    public UserInfo getThreadLocal(){
        return THREAD_LOCAL.get();
    }

    @Override
    public void afterCompletion(HttpServletRequest request,HttpServletResponse response,Object handler,Exception ex) throws Exception {
//        清空线程局部变量
        THREAD_LOCAL.remove();
    }
}
