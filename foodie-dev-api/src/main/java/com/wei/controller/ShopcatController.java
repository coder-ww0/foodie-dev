package com.wei.controller;

import com.wei.pojo.bo.ShopcartBO;
import com.wei.utils.WEIJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.WatchEvent;

/**
 * @author www
 * @date 2022/3/26 10:08
 * @description: TODO
 */

@Api(value = "购物车接口controller", tags = {"购物车接口相关的api"})
@RequestMapping("/shopcart")
@RestController
public class ShopcatController {

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public WEIJsonResult add(@RequestParam String userId, @RequestBody ShopcartBO shopcartBO
            , HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(userId)) {
            return WEIJsonResult.errorMsg("");
        }
        // TODO 前端用户在登录的情况下,添加商品到购物车,会同时在后端同步购物车到redis缓存
        return WEIJsonResult.ok();
    }
}
