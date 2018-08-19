package com.l0veyt.example.ssoserver.controller;

import com.l0veyt.example.ssoserver.common.TokenGenerator;
import com.l0veyt.example.ssoserver.common.TokenUserData;
import com.l0veyt.example.ssoserver.model.User;
import com.l0veyt.example.ssoserver.pojo.dto.LoginDto;
import com.l0veyt.example.ssoserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 跳转到登录页面
     * @return 登录页面路由
     */
    @GetMapping("/loginPage")
    public String loginPage(String originUrl, Model model) {
        model.addAttribute("test", "测试值");
        if (!StringUtils.isEmpty(originUrl)) {
            model.addAttribute("originUrl", originUrl);
        }
        return "login";
    }

    /**
     * 登录SSO应用
     * @param loginDto 登录DTO，包含用户账户、密码以及跳转到SSO之前要访问的源地址
     * @return 登录成功，跳转源地址。登录失败，重定向到登录页面
     */
    @PostMapping("/login")
    public String login(LoginDto loginDto, HttpServletResponse response, Model model) {
        try {
            // 根据用户名查询用户信息
            Optional<User> user = userService.loginVerify(loginDto);
            // 存在用户，校验密码，登录成功则跳转源地址
            Optional<String> verifyResult = user.map(User::getPassword).filter(password -> password.equals(loginDto.getPassword()));
            if (verifyResult.isPresent()) {
                // 生成Token
                String token = TokenGenerator.generator();
                // 存储到存放Token的Map集合中
                TokenUserData.addToken(token, user.get());
                // 存储到Cookie中
                Cookie tokenCookie = new Cookie("token", token);
                tokenCookie.setPath("/");
                tokenCookie.setHttpOnly(true);
                response.addCookie(tokenCookie);
                return "redirect:" + URLDecoder.decode(loginDto.getOriginUrl(), "UTF-8");
            }
            model.addAttribute("errorInfo", "账号或密码错误");
        } catch (Exception e) {
            model.addAttribute("errorInfo", "系统错误");
        }
        return "redirect:/user/loginPage";
    }
}
