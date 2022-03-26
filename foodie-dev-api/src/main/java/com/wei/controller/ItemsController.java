package com.wei.controller;

import com.wei.pojo.Items;
import com.wei.pojo.ItemsImg;
import com.wei.pojo.ItemsParam;
import com.wei.pojo.ItemsSpec;
import com.wei.pojo.vo.CommentLevelCountsVO;
import com.wei.pojo.vo.ItemInfoVO;
import com.wei.pojo.vo.ShopcartVO;
import com.wei.service.ItemService;
import com.wei.utils.PagedGridResult;
import com.wei.utils.WEIJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.awt.geom.AreaOp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author www
 * @date 2022/3/24 16:18
 * @description: TODO
 */

@Api(value = "商品接口", tags = {"商品信息展示的相关接口"})
@RestController
@RequestMapping("/items")
public class ItemsController extends BaseController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public WEIJsonResult getItemInfo(@ApiParam(name = "itemId", value = "商品id", required = true)
                                         @PathVariable String itemId) {
        if (itemId == null) {
            return WEIJsonResult.errorMsg(null);
        }
        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemsImgList);
        itemInfoVO.setItemSpecList(itemsSpecList);
        itemInfoVO.setItemParams(itemsParam);
        return WEIJsonResult.ok(itemInfoVO);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public WEIJsonResult commentLevel(@RequestParam String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return WEIJsonResult.errorMsg(null);
        }
        CommentLevelCountsVO commentLevelCountsVO = itemService.queryCommentCounts(itemId);
        return WEIJsonResult.ok(commentLevelCountsVO);
    }

    @ApiOperation(value = "查看商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public WEIJsonResult comments(@RequestParam String itemId, @RequestParam Integer level
            , @RequestParam Integer page, @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(itemId)) {
            return WEIJsonResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }
        PagedGridResult gridResult = itemService.queryPagedComments(itemId, level, page, pageSize);
        return WEIJsonResult.ok(gridResult);
    }

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public WEIJsonResult search(@RequestParam String keywords, @RequestParam String sort
            , @RequestParam Integer page, @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(keywords)) {
            return WEIJsonResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult gridResult = itemService.searchItems(keywords, sort, page, pageSize);
        return WEIJsonResult.ok(gridResult);
    }

    @ApiOperation(value = "通过分类id搜索商品列表", notes = "通过分类id搜索商品列表", httpMethod = "GET")
    @GetMapping("/catItems")
    public WEIJsonResult catItems(@RequestParam Integer catId, @RequestParam String sort
            , @RequestParam Integer page, @RequestParam Integer pageSize) {
        if (catId == null) {
            return WEIJsonResult.errorMsg(null);
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult gridResult = itemService.searchItems(catId, sort, page, pageSize);
        return WEIJsonResult.ok(gridResult);
    }

    /**
     * 用于用户长时间未登陆用户，刷新购物车中的数据
     */
    @ApiOperation(value = "根据商品规格ids查找最新商品数据", notes = "根据商品规格ids查找最新商品数据", httpMethod = "GET")
    @GetMapping("/refresh")
    public WEIJsonResult refresh(@RequestParam String itemSpecIds) {
        if (StringUtils.isBlank(itemSpecIds)) {
            return WEIJsonResult.ok();
        }
        List<ShopcartVO> list = itemService.queryItemsBySpecIds(itemSpecIds);

        return WEIJsonResult.ok(list);
    }

    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public WEIJsonResult del(@RequestParam String userId, @RequestParam String itemSpecId
            , HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return WEIJsonResult.errorMsg("");
        }
        // TODO 用户在页面删除购物车中的商品数据,如果用户已经登录,则需要同步删除后端购物车的数据
        return WEIJsonResult.ok();
    }

}
