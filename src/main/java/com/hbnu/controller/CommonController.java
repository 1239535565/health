package com.hbnu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hbnu.annotation.LoginToken;
import com.hbnu.entity.User;
import com.hbnu.service.DepartmentService;
import com.hbnu.service.UserService;
import com.hbnu.utils.Result;
import com.hbnu.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "adminApi/common")
public class CommonController {
    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;


    /*
        表单校验 新增用户时验证库里是否有重复用户名
     */
    @GetMapping("/checkUsername/{username}")
    @LoginToken
    public Object checkUsername(@PathVariable String username){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        if (userService.getOne(userQueryWrapper)!=null){
            return ResultGenerator.genSuccessResult(true);
        }
        return ResultGenerator.genSuccessResult(false);
    }


    /*
        表单校验 修改密码时验证旧密码是否正确
     */
    @GetMapping("/checkPassword")
    public String checkPassword(String password){
        return "";
    }


    /*
        获取科室下拉框
     */
    @GetMapping("/getDepartment")
    public Result getDepartment(){
        return ResultGenerator.genSuccessResult(departmentService.list());
    }

    /*
       通过科室id查询医生
    */
    @GetMapping("/getDoctor")
    @ResponseBody
    public String getDoctorById(Integer id){
        return "";
    }

    /*
        通过用户名查询医生详情
     */
    @GetMapping("/getDoctorInfoByUsername")
    @ResponseBody
    public String getDoctorInfoByUsername(String username){
        return "";
    }
}