package com.l0veyt.example.ssoserver.common;

import com.l0veyt.example.ssoserver.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Token存储介质类
 */
public class TokenUserData {

    /**
     * 私有化构造器
     */
    private TokenUserData() {}

    /**
     * 存储Token与用户关联信息的Map集合
     * Token为key
     * User对象为value
     */
    private static Map<String, User> userDataMap = new HashMap<>();

    /**
     * 添加Token/User关联信息
     * @param token 随机的UUID
     * @param user 登录用户的实体类
     */
    public static void addToken(String token, User user) {
        userDataMap.put(token, user);
    }

    /**
     * 校验Token有效性
     * @param token 登录用户对应的Token
     * @return 登录用户的实体类
     */
    public static User verifyToken(String token) {
        return userDataMap.get(token);
    }

    /**
     * 移除Token/User关联信息
     * @param token 登录用户对应的Token
     */
    public static void removeToken(String token) {
        userDataMap.remove(token);
    }
}