package com.py.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("order")
public class OrderController {

    @RequestMapping("save")
    public String save(){
        System.out.println("进入方法");

        //基于角色
        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        //代码方式
        if (subject.hasRole("admin")) {
            System.out.println("保存订单!");
        }else{
            System.out.println("无权访问!");
        }
        //基于权限字符串
        //....

        return "redirect:/index.jsp";
    }

    @RequiresRoles(value={"admin","user"})//用来判断角色  同时具有 admin user
    @RequiresPermissions("user:update:01") //用来判断权限字符串
    @RequestMapping("save2")
    public String save2(){
        System.out.println("进入方法");
        return "redirect:/index.jsp";
    }
 }
