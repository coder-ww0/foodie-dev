package com.wei.service;

import com.wei.pojo.Carousel;

import java.util.List;

/**
 * @author www
 * @date 2022/3/24 11:14
 * @description:
 */
public interface CarouselService {

    /**
     * 查询所有轮播图列表
     * @param isShow
     * @return
     */
    List<Carousel> queryAll(Integer isShow);
}
