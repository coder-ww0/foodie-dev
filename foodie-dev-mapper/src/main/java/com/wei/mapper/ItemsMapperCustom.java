package com.wei.mapper;

import com.wei.my.mapper.MyMapper;
import com.wei.pojo.Items;
import com.wei.pojo.ItemsComments;
import com.wei.pojo.vo.ItemCommentVO;
import com.wei.pojo.vo.SearchItemsVO;
import com.wei.pojo.vo.ShopcartVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ItemsMapperCustom {
    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);

    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);

    List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);

    List<ShopcartVO> queryItemsBySpecIds(@Param("paramsList") List specIdsList);
}