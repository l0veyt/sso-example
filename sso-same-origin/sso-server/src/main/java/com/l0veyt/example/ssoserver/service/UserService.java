package com.l0veyt.example.ssoserver.service;

import com.l0veyt.example.ssoserver.model.User;
import com.l0veyt.example.ssoserver.pojo.dto.LoginDto;

import java.util.Optional;

public interface UserService {

    /**
     * 登录校验
     * @param loginDto 包含账号/密码
     * @return 账户信息
     */
    Optional<User> loginVerify(LoginDto loginDto);
}
