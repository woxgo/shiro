package com.py.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/index")
    public String hello(){
        System.out.println("hello 主页");
        return "index";
    }
}
