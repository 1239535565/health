package com.hbnu.controller;

import com.hbnu.entity.Registration;
import com.hbnu.service.RegistrationService;
import com.hbnu.utils.Result;
import com.hbnu.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@Controller
@RequestMapping("/adminApi/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    /*
        查询挂号列表
     */
    @GetMapping("/list")
    @ResponseBody
    public Result listRegistration(){
        List<Registration> list = registrationService.list();
        return ResultGenerator.genSuccessResult(list);
    }

    /*
        新增挂号,给参数
     */
    @PostMapping("/")
    @ResponseBody
    public String add(){
        return "";
    }

    /*
        开发票,给id
     */
    @PutMapping("/")
    @ResponseBody
    public String openInvoice(Integer id){
        return "";
    }

    /*
        通过id获取信息，给id
     */
    @GetMapping("/")
    @ResponseBody
    public String getInfoById(Integer id){
        return "";
    }

    /*
        采纳，给id
     */
    @GetMapping("/accept")
    @ResponseBody
    public String accept(Integer id){
        return "";
    }

    /*
        不采纳，给id
     */
    @GetMapping("/notAccept")
    @ResponseBody
    public String notAccept(Integer id){
        return "";
    }

    /*
        评价,给参数
     */
    @PutMapping("/evaluate")
    @ResponseBody
    public String evaluate(){
        return "";
    }

}
