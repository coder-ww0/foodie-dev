package com.wei.service.impl;

import com.wei.mapper.CarouselMapper;
import com.wei.pojo.Carousel;
import com.wei.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author www
 * @date 2022/3/24 11:16
 * @description: TODO
 */

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    @Override
    public List<Carousel> queryAll(Integer isShow) {
        Example example = new Example(Carousel.class);
        // 使用倒序
        example.orderBy("sort").desc();
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("isShow", isShow);
        List<Carousel> result = carouselMapper.selectByExample(example);
        return result;
    }
}
