package com.wei.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wei.enums.CommentLevel;
import com.wei.mapper.*;
import com.wei.pojo.*;
import com.wei.pojo.vo.CommentLevelCountsVO;
import com.wei.pojo.vo.ItemCommentVO;
import com.wei.pojo.vo.SearchItemsVO;
import com.wei.pojo.vo.ShopcartVO;
import com.wei.service.ItemService;
import com.wei.utils.DesensitizationUtil;
import com.wei.utils.PagedGridResult;
import com.wei.utils.WEIJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * @author www
 * @date 2022/3/24 15:51
 * @description: TODO
 */

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    @Override
    public Items queryItemById(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {
        Example example = new Example(ItemsImg.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsImgMapper.selectByExample(example);
    }

    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example example = new Example(ItemsSpec.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsSpecMapper.selectByExample(example);
    }

    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example example = new Example(ItemsParam.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId", itemId);
        return itemsParamMapper.selectOneByExample(example);
    }

    @Override
    public CommentLevelCountsVO queryCommentCounts(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;

        CommentLevelCountsVO commentLevelCountsVO = new CommentLevelCountsVO();
        commentLevelCountsVO.setTotalCounts(totalCounts);
        commentLevelCountsVO.setGoodCounts(goodCounts);
        commentLevelCountsVO.setNormalCounts(normalCounts);
        commentLevelCountsVO.setBadCounts(badCounts);
        return commentLevelCountsVO;
    }

    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("itemId", itemId);
        map.put("level", level);

        PageHelper.startPage(page, pageSize);
        List<ItemCommentVO> list = itemsMapperCustom.queryItemComments(map);
        for (ItemCommentVO item : list) {
            item.setNickname(DesensitizationUtil.commonDisplay(item.getNickname()));
        }
        PagedGridResult grid = setterPagedGrid(list, page);

        return grid;
    }

    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("keywords", keywords);
        map.put("sort", sort);
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> list = itemsMapperCustom.searchItems(map);
        return setterPagedGrid(list, page);
    }

    @Override
    public PagedGridResult searchItems(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("catId", catId);
        map.put("sort", sort);
        PageHelper.startPage(page, pageSize);
        List<SearchItemsVO> list = itemsMapperCustom.searchItemsByThirdCat(map);
        return setterPagedGrid(list, page);
    }

    @Override
    public List<ShopcartVO> queryItemsBySpecIds(String specIds) {
        String[] ids = specIds.split(",");
        List<String> specIdsList = new ArrayList<>();
        Collections.addAll(specIdsList, ids);
        return itemsMapperCustom.queryItemsBySpecIds(specIdsList);
    }

    Integer getCommentCounts(String itemId, Integer level) {
        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);
        if (level != null) {
            condition.setCommentLevel(level);
        }
        return itemsCommentsMapper.selectCount(condition);
    }

    private PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
