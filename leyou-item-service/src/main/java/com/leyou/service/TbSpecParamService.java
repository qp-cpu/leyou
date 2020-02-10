package com.leyou.service;

import com.leyou.entity.TbSpecParam;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * <p>
 * 规格参数组下的参数名 服务类
 * </p>
 *
 * @author qp
 * @since 2020-02-09
 */
public interface TbSpecParamService extends IService<TbSpecParam> {

    List<TbSpecParam> queryTbSpecParamByGid(Long gid);

    int deleteTbSpecgroup(Long id);

    int updatespecparem(TbSpecParam tbSpecParam);

    int addspecparem(TbSpecParam tbSpecParam);
}
