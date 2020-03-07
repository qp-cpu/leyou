package com.leyoou.client;

import com.leyoou.pojo.TbUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user-service")
public interface UserClient {

    /**
     * 查询用户根据用户名和密码
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    public TbUser queryUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password
    );
}
