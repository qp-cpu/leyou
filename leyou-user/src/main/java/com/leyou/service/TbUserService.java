package com.leyou.service;

import com.leyou.entity.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author qp
 * @since 2020-02-28
 */
public interface TbUserService extends IService<TbUser> {

    Boolean checkUser(String data,Integer type);

    void sendVerifyCode(String phone);

    void register(TbUser user);

    TbUser queryUser(String username,String password);
}
