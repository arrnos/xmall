package cn.exrick.front.controller;

import cn.exrick.common.pojo.Result;
import cn.exrick.common.pojo.SearchResult;
import cn.exrick.common.utils.ResultUtil;
import cn.exrick.content.service.ContentService;
import cn.exrick.manager.dto.front.Product;
import cn.exrick.manager.dto.front.ProductDet;
import cn.exrick.manager.dto.front.ProductHome;
import cn.exrick.search.service.SearchItemService;
import cn.exrick.search.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(description = "商品页面展示")
public class GoodsController {

    @Autowired
    private ContentService contentService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping(value = "/goods/productHome",method = RequestMethod.GET)
    @ApiOperation(value = "首页商品展示")
    public Result<ProductHome> getProductHome(){

        ProductHome productHome=contentService.getProductHome();
        return new ResultUtil<ProductHome>().setData(productHome);
    }

    @RequestMapping(value = "/goods/productDet",method = RequestMethod.GET)
    @ApiOperation(value = "商品详情")
    public Result<ProductDet> getProductDet(Long productId){

        ProductDet productDet=contentService.getProductDet(productId);
        return new ResultUtil<ProductDet>().setData(productDet);
    }

    @RequestMapping(value = "/goods/computer",method = RequestMethod.GET)
    @ApiOperation(value = "所有商品")
    public Result<List<Product>> getAllProduct(@RequestParam(defaultValue = "1") int page,
                                               @RequestParam(defaultValue = "20") int size,
                                               @RequestParam(defaultValue = "") String sort,
                                               @RequestParam(defaultValue = "-1") int priceGt,
                                               @RequestParam(defaultValue = "-1") int priceLte){

        List<Product> list=contentService.getAllProduct(page,size,sort,priceGt,priceLte);
        return new ResultUtil<List<Product>>().setData(list);
    }

    @RequestMapping(value = "/goods/search/{key}",method = RequestMethod.GET)
    @ApiOperation(value = "搜索商品ES")
    public Result<SearchResult> searchProduct(@PathVariable String key,
                                              @RequestParam(defaultValue = "1") int page,
                                              @RequestParam(defaultValue = "20") int size){

        SearchResult searchResult=searchService.search(key,page,size);
        return new ResultUtil<SearchResult>().setData(searchResult);
    }

    @RequestMapping(value = "/goods/importIndex",method = RequestMethod.GET)
    @ApiOperation(value = "导入商品索引至ES")
    public Result<Object> searchProduct(){

        searchItemService.importAllItems();
        return new ResultUtil<Object>().setData(null);
    }

}
