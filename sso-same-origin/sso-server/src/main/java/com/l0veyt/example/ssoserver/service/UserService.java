package com.l0veyt.example.ssoserver.service;

public interface UserService {

    void login(String username, String password, String originUrl);
}
