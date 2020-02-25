package com.leyou.pojo;

import lombok.Data;

import java.util.List;
import java.util.Map;


public class SearchResult<G> extends PageList<Goods> {
    private List<Map<String,Object>> categories;

    private List<TbBrand> brands;

    private List<Map<String,Object>> specs;



    public List<Map<String, Object>> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Map<String, Object>> specs) {
        this.specs = specs;
    }

    public List<Map<String, Object>> getCategories() {
        return categories;
    }

    public void setCategories(List<Map<String, Object>> categories) {
        this.categories = categories;
    }

    public List<TbBrand> getBrands() {
        return brands;
    }

    public void setBrands(List<TbBrand> brands) {
        this.brands = brands;
    }

    public SearchResult(Long total,Integer totalPage,List<Goods> items) {
        super( total,totalPage,items );
    }

    public SearchResult(Long total,Integer totalPage,List<Goods> items,List<Map<String, Object>> categories,List<TbBrand> brands,List<Map<String, Object>> specs) {
        super( total,totalPage,items );
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }
}
