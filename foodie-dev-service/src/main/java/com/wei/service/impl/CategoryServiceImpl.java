package com.wei.service.impl;

import com.wei.mapper.CategoryMapper;
import com.wei.mapper.CategoryMapperCustom;
import com.wei.pojo.Category;
import com.wei.pojo.vo.CategoryVO;
import com.wei.pojo.vo.NewItemsVO;
import com.wei.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author www
 * @date 2022/3/24 13:04
 * @description: TODO
 */

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<Category> queryAllRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);
        List<Category> result = categoryMapper.selectByExample(example);
        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatId) {
        return categoryMapperCustom.getSubCatList(rootCatId);
    }

    @Override
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("rootCatId", rootCatId);
        return categoryMapperCustom.getSixNewItemsLazy(map);
    }
}
