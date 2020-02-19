package com.leyou.dto;

import com.leyou.entity.TbSku;
import com.leyou.entity.TbSpu;
import com.leyou.entity.TbSpuDetail;
import lombok.Data;

import java.util.List;

@Data
public class SpuDto extends TbSpu {

    private String cname;

    private String bname;

    private TbSpuDetail spuDetail;

    private List<TbSku> skus;
}
