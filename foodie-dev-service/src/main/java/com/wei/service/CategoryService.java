package com.wei.service;

import com.wei.pojo.Carousel;
import com.wei.pojo.Category;
import com.wei.pojo.vo.CategoryVO;
import com.wei.pojo.vo.NewItemsVO;
import org.apache.ibatis.type.IntegerTypeHandler;

import java.util.List;

/**
 * @author www
 * @date 2022/3/24 13:03
 * @description: TODO
 */

public interface CategoryService {
    /**
     * 查询所有一级分类
     * @return
     */
    List<Category> queryAllRootLevelCat();

    /**
     * 根据一句分类id查询子信息
     * @param rootCatId
     * @return
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的6条最新商品数据
     * @param rootCatId
     * @return
     */
    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
