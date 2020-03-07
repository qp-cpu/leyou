package com.leyou.filter;

import com.leyou.config.FilterProperties;
import com.leyou.config.JwtProperties;
import com.leyou.pojo.UserInfo;
import com.leyou.utils.CookieUtils;
import com.leyou.utils.JwtUtils;
import com.netflix.discovery.converters.Auto;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.protocol.RequestContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
@EnableConfigurationProperties({JwtProperties.class,FilterProperties.class})
public class ZuulFilter extends com.netflix.zuul.ZuulFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private FilterProperties filterProperties;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 10;
    }

    @Override
    public boolean shouldFilter() {
        List<String> paths = this.filterProperties.getAllowPaths();
        //        初始化ZUUL网关的上下文
        RequestContext content = RequestContext.getCurrentContext();

//        获取 request对象
        HttpServletRequest request = content.getRequest();
//        获取请求路径
        String path = request.getRequestURI().toString();

        for (String url : paths) {
            if(path.contains( url ))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {

//        初始化ZUUL网关的上下文
        RequestContext content = RequestContext.getCurrentContext();

//        获取 request对象
        HttpServletRequest request = content.getRequest();
        String             token = CookieUtils.getCookieValue( request,this.jwtProperties.getCookieName() );
        if(StringUtils.isBlank( token ))
        {
            content.setSendZuulResponse( false );
            content.setResponseStatusCode( HttpStatus.UNAUTHORIZED.value() );
        }
        try {
          JwtUtils.getInfoFromToken( token,this.jwtProperties.getPublicKey() );
        } catch (Exception e) {
            content.setSendZuulResponse( false );
            content.setResponseStatusCode( HttpStatus.UNAUTHORIZED.value() );
        }
        return null;
    }
}
