package com.wei.controller;

import com.mysql.cj.PreparedQuery;
import com.wei.pojo.Users;
import com.wei.pojo.bo.UserBO;
import com.wei.service.UserService;
import com.wei.utils.CookieUtils;
import com.wei.utils.JsonUtils;
import com.wei.utils.MD5Utils;
import com.wei.utils.WEIJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author www
 * @date 2022/3/22 19:40
 * @description: TODO
 */

@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("/passport")
public class PassportController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "判断用户是否存在", notes = "判断用户是否存在note", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    public WEIJsonResult usernameIsExist(@RequestParam String username) {
        // 1. 判断用户名不能为空
        if (StringUtils.isBlank(username)) {
            return WEIJsonResult.errorMsg("用户名不能为空");
        }

        // 2. 查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return WEIJsonResult.errorMsg("用户名已经存在");
        }

        // 3.请求成功,用户名没有重复
        return WEIJsonResult.ok();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public WEIJsonResult regist(@RequestBody UserBO userBO
            , HttpServletRequest request
            , HttpServletResponse response) {

        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPwd = userBO.getConfirmPassword();

        // 0. 判断用户名和密码不为空
        if (StringUtils.isBlank(username)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(confirmPwd)) {
            return WEIJsonResult.errorMsg("用户名或密码不能为空");
        }
        // 1.查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return WEIJsonResult.errorMsg("用户名已经存在");
        }
        // 2. 密码的长度不能少于6位
        if (password.length() < 6) {
            return WEIJsonResult.errorMsg("密码长度不能少于6");
        }
        // 3.判断两次密码是否一致
        if (!password.equals(confirmPwd)) {
            return WEIJsonResult.errorMsg("两次密码输入不一致");
        }
        // 4.实现注册
        Users userResult = userService.createUser(userBO);

        userResult = setNullProperty(userResult);

        // 设置cookie的相关信息
        CookieUtils.setCookie(request, response, "user"
                , JsonUtils.objectToJson(userResult), true);


        return WEIJsonResult.ok();

    }
    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public WEIJsonResult login(@RequestBody UserBO userBO
            , HttpServletRequest request
            , HttpServletResponse response) throws Exception {

        String username = userBO.getUsername();
        String password = userBO.getPassword();

        // 0. 判断用户名和密码不为空
        if (StringUtils.isBlank(username)
                || StringUtils.isBlank(password)) {
            return WEIJsonResult.errorMsg("用户名或密码不能为空");
        }

        // 1.实现登录
        Users userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));

        if (userResult == null) {
            return WEIJsonResult.errorMsg("用户名或密码不正确");
        }
        userResult = setNullProperty(userResult);

        // 设置cookie的相关信息
        CookieUtils.setCookie(request, response, "user"
                , JsonUtils.objectToJson(userResult), true);

        return WEIJsonResult.ok(userResult);

    }

    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public WEIJsonResult logout(@RequestParam String userId
            , HttpServletRequest request, HttpServletResponse response) {

        // 清除用户相关信息的cookie即可实现登出
        CookieUtils.deleteCookie(request, response, "user");
        // TODO 用户退出登录,需要清空购物车
        // TODO 分布式会话中需要清除用户数据
        return WEIJsonResult.ok();
    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }
}
