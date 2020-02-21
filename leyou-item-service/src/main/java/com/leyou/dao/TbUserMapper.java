package com.leyou.dao;

import com.leyou.entity.TbUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author qp
 * @since 2020-02-20
 */
@Mapper
public interface TbUserMapper extends BaseMapper<TbUser> {

}
