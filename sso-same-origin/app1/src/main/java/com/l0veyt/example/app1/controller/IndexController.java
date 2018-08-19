package com.l0veyt.example.app1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    /**
     * 跳转到APP1应用的首页
     * @return 首页页面
     */
    @GetMapping("/indexPage")
    public String indexPage() {
        return "index";
    }
}
