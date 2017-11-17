package com.xxg.jcatch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by wucao on 17/3/16.
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        return "redirect:/app/index";
    }
}
