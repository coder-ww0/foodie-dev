package com.wei.mapper;

import com.wei.my.mapper.MyMapper;
import com.wei.pojo.Items;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface ItemsMapper extends MyMapper<Items> {
}