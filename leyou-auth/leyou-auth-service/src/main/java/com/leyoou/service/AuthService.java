package com.leyoou.service;

import com.leyoou.client.UserClient;
import com.leyoou.config.JwtProperties;
import com.leyoou.pojo.TbUser;
import com.leyou.pojo.UserInfo;
import com.leyou.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private UserClient userClient;

    /**
     * 生成token
     * @param username
     * @param password
     * @return
     */
    public String accredit(String username,String password) {
//        根据用户名和密码查询
        TbUser user = this.userClient.queryUser( username,password );
//        判断user为空
         if(user == null)
         {
             return null;
         }
//        jwtUtils生成token
        try {
            UserInfo userInfo = new UserInfo(  );
            userInfo.setId( user.getId() );
            userInfo.setUsername( user.getUsername() );
          return  JwtUtils.generateToken( userInfo, this.jwtProperties.getPrivateKey(),this.jwtProperties.getExpire());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
