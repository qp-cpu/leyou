package com.leyou.pojo;

import lombok.Data;
import java.util.List;

@Data
public class SpuDto extends TbSpu {

    private String cname;

    private String bname;

    private TbSpuDetail spuDetail;

    private List<TbSku> skus;
}
