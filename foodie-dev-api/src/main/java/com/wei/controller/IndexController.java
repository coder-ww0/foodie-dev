package com.wei.controller;

import com.wei.enums.YesOrNo;
import com.wei.pojo.Carousel;
import com.wei.pojo.Category;
import com.wei.pojo.vo.CategoryVO;
import com.wei.pojo.vo.NewItemsVO;
import com.wei.service.CarouselService;
import com.wei.service.CategoryService;
import com.wei.utils.WEIJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author www
 * @date 2022/3/24 11:23
 * @description: TODO
 */

@RestController
@Api(value = "首页", tags = {"首页展示的相关接口"})
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public WEIJsonResult carousel() {
        List<Carousel> list = carouselService.queryAll(YesOrNo.YES.type);
        return WEIJsonResult.ok(list);
    }


    /**
     * 首页分类展示需求
     * 1. 第一次刷新主页查询大分类,渲染展示到首页
     * 2. 如果鼠标上移到大分类,则加载其子分类的内容,如果已经存在子分类,则不需要进行加载(懒加载)
     */
    @ApiOperation(value = "用户获取商品分类(一级分类)", notes = "用户获取商品分类(一级分类)", httpMethod = "GET")
    @GetMapping("/cats")
    public WEIJsonResult cats() {
        List<Category> list = categoryService.queryAllRootLevelCat();
        return WEIJsonResult.ok(list);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public WEIJsonResult subCat(@ApiParam(name = "rootCatId", value = "一级分类id", required = true)
                                    @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return WEIJsonResult.errorMsg("分类不存在");
        }

        List<CategoryVO> list = categoryService.getSubCatList(rootCatId);

        return WEIJsonResult.ok(list);
    }

    @ApiOperation(value = "查询每个一级分类下最新的6条商品数据", notes = "查询每个一级分类下最新的6条商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public WEIJsonResult sixNewItems(@ApiParam(name = "rootCatId", value = "一级分类id", required = true)
                                         @PathVariable Integer rootCatId) {
        if (rootCatId == null) {
            return WEIJsonResult.errorMsg("分类不存在");
        }
        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);
        return WEIJsonResult.ok(list);
    }



}
