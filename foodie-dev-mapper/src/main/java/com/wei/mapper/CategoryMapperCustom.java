package com.wei.mapper;

import com.wei.my.mapper.MyMapper;
import com.wei.pojo.Category;
import com.wei.pojo.vo.CategoryVO;
import com.wei.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CategoryMapperCustom {
    /**
     * 查询子分类
     * @param rootCatId
     * @return
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 首页下最新的6条商品相关数据
     * @return
     */
    List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap")Map<String, Object> paramsMap);
}