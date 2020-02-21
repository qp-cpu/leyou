package com.leyou.controller;

import com.leyou.pojo.Goods;
import com.leyou.pojo.PageList;
import com.leyou.pojo.SearchRequest;
import com.leyou.pojo.SearchResult;
import com.leyou.serice.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    /**
     * 查找分页Goods结果集
     * @param request
     * @return
     */
    @PostMapping("page")
    public ResponseEntity<SearchResult<Goods>> search(@RequestBody SearchRequest request)
    {
        SearchResult<Goods> pageList = this.searchService.search(request);
        if (pageList == null || CollectionUtils.isEmpty( pageList.getItems() ))
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok( pageList );
    }
}
