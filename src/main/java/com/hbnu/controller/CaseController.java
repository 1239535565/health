package com.hbnu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/adminApi/case")
public class CaseController {

    /*
        开处方，给参数
     */
    @PostMapping("/makeCase")
    @ResponseBody
    public String makeCase(){
        return "";
    }

    /*
        查询列表，给参数
     */
    @GetMapping("/list")
    @ResponseBody
    public String listCase(){
        return "";
    }

}
