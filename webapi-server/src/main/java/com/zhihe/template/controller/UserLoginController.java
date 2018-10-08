package com.zhihe.template.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletResponse;

@RestController
public class UserLoginController {
    @RequestMapping("echo/{name}")
    public String echo(@PathVariable String name) {
        String message = name;
        return message;
    }

}