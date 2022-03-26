package com.wei.service;

import com.wei.pojo.Items;
import com.wei.pojo.ItemsImg;
import com.wei.pojo.ItemsParam;
import com.wei.pojo.ItemsSpec;
import com.wei.pojo.vo.CommentLevelCountsVO;
import com.wei.pojo.vo.ItemCommentVO;
import com.wei.pojo.vo.ShopcartVO;
import com.wei.utils.PagedGridResult;

import java.util.List;

/**
 * @author www
 * @date 2022/3/24 15:49
 * @description: TODO
 */
public interface ItemService {

    /**
     * 根据商品id查询详情
     * @param id
     * @return
     */
    Items queryItemById(String itemId);

    /**
     * 根据商品id查询商品列表
     * @param itemId
     * @return
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格
     * @param itemId
     * @return
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     * @param itemId
     * @return
     */
    ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询商品的评价等级数量
     * @param itemId
     * @return
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品id查询商品的评价(分页)
     * @param itemId
     * @param level
     * @return
     */
    PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize);

    /**
     * 搜索商品列表
     * @param keywords
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItems(String keywords, String sort
            , Integer page, Integer pageSize);

    /**
     * 根据分类id搜索商品列表
     * @param catId
     * @param sort
     * @param page
     * @param pageSize
     * @return
     */
    PagedGridResult searchItems(Integer catId, String sort
            , Integer page, Integer pageSize);


    /**
     * 根据规格ids查询最新的购物车中的商品数据(用于刷新渲染购物车中的商品数据)
     * @param specIds
     * @return
     */
    List<ShopcartVO> queryItemsBySpecIds(String specIds);
}
