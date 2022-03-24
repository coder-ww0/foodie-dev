package com.wei.service;

import com.wei.pojo.Users;
import com.wei.pojo.bo.UserBO;

/**
 * @author www
 * @date 2022/3/22 19:29
 * @description: TODO
 */

public interface UserService {
    /**
     * 判断用户名是否存在
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 创建用户
     */
    Users createUser(UserBO userBO);

    /**
     * 用于登录
     */
    Users queryUserForLogin(String username, String password);
}
