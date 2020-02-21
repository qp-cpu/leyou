package com.leyou.service.impl;

import com.leyou.entity.TbUser;
import com.leyou.dao.TbUserMapper;
import com.leyou.service.TbUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author qp
 * @since 2020-02-20
 */
@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService {

}
