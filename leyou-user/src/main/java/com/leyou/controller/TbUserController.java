package com.leyou.controller;


import com.leyou.entity.TbUser;
import com.leyou.service.TbUserService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.validation.Valid;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author qp
 * @since 2020-02-28
 */
@Controller
public class TbUserController {

    @Autowired
    private TbUserService userService;

    /**
     * 判断用户可不可用
     * @param data
     * @param type
     * @return
     */
    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean>  checkUser(@PathVariable("data") String data,
                                              @PathVariable("type") Integer type)
       {
         Boolean bool  = this.userService.checkUser(data,type);
         if(bool == null)
         {
             return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
         }
         return ResponseEntity.ok( bool );
       }

    /**
     * 发送短信
     * @param phone
     * @return
     */
       @PostMapping("code")
    public ResponseEntity<Void> sendVerifyCode(@RequestParam("phone") String phone)
       {
           this.userService.sendVerifyCode(phone);
           return ResponseEntity.status( HttpStatus.CREATED ).build();
       }

    /**
     * 用户注册
     * @param user
     * @return
     */
       @PostMapping("register")
    public ResponseEntity<Void> register(@Valid TbUser user){
           this.userService.register(user);
          return new ResponseEntity<>( HttpStatus.OK );
       }


    /**
     * 查询用户根据用户名和密码
     * @param username
     * @param password
     * @return
     */
       @GetMapping("query")
    public ResponseEntity<TbUser> queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password
       )
       {
            TbUser user  = this.userService.queryUser(username,password);
            if(user == null)
            {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(user);
       }


}
