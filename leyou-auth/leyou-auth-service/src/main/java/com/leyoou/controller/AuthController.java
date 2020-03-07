package com.leyoou.controller;

import com.leyoou.config.JwtProperties;
import com.leyoou.service.AuthService;
import com.leyou.pojo.UserInfo;
import com.leyou.utils.CookieUtils;
import com.leyou.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {


    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 生成token
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping("accredit")
    public ResponseEntity<Void> accredit(@RequestParam("username") String username,
                                         @RequestParam("password") String password,
                                         HttpServletRequest request,
                                         HttpServletResponse response)
    {
     String token  =  this.authService.accredit(username,password);
     if(StringUtils.isBlank( token ))
     {
         return ResponseEntity.status( HttpStatus.UNAUTHORIZED ).build();
     }
     CookieUtils.setCookie( request,response,jwtProperties.getCookieName(),token,this.jwtProperties.getExpire()*60 );
     return ResponseEntity.ok().build();
    }


    /**
     * 解析token生成userINfo
     * @param token
     * @return
     */
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verify(@CookieValue("LY_TOKEN") String token,
                                           HttpServletRequest request,
                                           HttpServletResponse response){

        try {
            //通过jwt的工具类解析jwt
            UserInfo userInfo = JwtUtils.getInfoFromToken( token,this.jwtProperties.getPublicKey() );
            if(userInfo == null)
            {
                return ResponseEntity.status( HttpStatus.UNAUTHORIZED ).build();
            }
//            刷新jwt过期时间
            token  = JwtUtils.generateToken( userInfo,this.jwtProperties.getPrivateKey(),this.jwtProperties.getExpire() );
//            刷新cookie中的有效时间
            CookieUtils.setCookie( request,response,this.jwtProperties.getCookieName(),token,this.jwtProperties.getExpire()*60);

            return ResponseEntity.ok( userInfo );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status( HttpStatus.UNAUTHORIZED ).build();
    }



 }
