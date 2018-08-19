package com.l0veyt.example.ssoserver.common;

import java.util.UUID;

/**
 * Token生成工具类
 */
public class TokenGenerator {

    /**
     * 私有化构造器
     */
    private TokenGenerator() {}

    /**
     * 生成一个Token
     * @return 由32位UUID构成的Token
     */
    public static String generator() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
