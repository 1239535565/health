package com.hbnu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.hbnu.annotation.LoginToken;
import com.hbnu.entity.User;
import com.hbnu.service.UserService;
import com.hbnu.utils.MD5Util;
import com.hbnu.utils.QueryRequest;
import com.hbnu.utils.Result;
import com.hbnu.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Date;


@RestController
@RequestMapping("/adminApi/patiens")
public class PatientsController {

    @Autowired
    private UserService userService;

    /*
        查询用户列表,给参数
     */
    @GetMapping("/list")
    @LoginToken
    public Result listUser(QueryRequest queryRequest, User user){
        return ResultGenerator.genSuccessResult(userService.queryPatientsUser(queryRequest,user));
    }

    /*
        查询用户详细,给id
     */
    @GetMapping("/{userId}")
    public Result getUser(@PathVariable Integer userId){
        User user = userService.getById(userId);
        return ResultGenerator.genSuccessResult(user);
    }

    /*
        新增用户,给参数
     */
    @PostMapping
    public Result insert(@RequestBody User entity){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",entity.getUsername());
        User user = userService.getOne(userQueryWrapper);
        if (ObjectUtils.isNull(user)){
            //password,last_login_ip,last_login_time,avatar,school,dep_id,create_time,modify_time,birthday,role_id
            entity.setCreate_time(new Date());
            entity.setPassword(MD5Util.MD5Encode("123765","utf-8"));
            entity.setRole_id(2);
            userService.save(entity);
            return ResultGenerator.genSuccessResult("用户"+entity.getUsername()+"新增成功，默认密码为：123765");
        }
        return ResultGenerator.genErrorResult(201,"用户新增失败，"+entity.getUsername()+"已存在");
    }

    /*
        修改用户,给参数
     */
    @PutMapping
    public Result updateUser(@RequestBody User user){
        userService.updateById(user);
        return ResultGenerator.genSuccessResult();
    }

    /*
        删除用户，给id
     */
    @DeleteMapping("/{userIds}")
    public Result delUser(@PathVariable Integer[] userIds){
        boolean flag = false;
        for (Integer userId : userIds) {
            User user = userService.getById(userId);
            if (!user.getUsername().equals("admin")){
                userService.removeById(userId);
            }else {
                flag=true;
            }
        }
        if (flag){
            return ResultGenerator.genFailResult("admin用户不能删除");
        }else {
            return ResultGenerator.genSuccessResult();
        }
    }

    /*
        用户密码重置,给id，密码，用户名
     */
    @PutMapping("/resetPwd")
    public Result resetUserPwd(@RequestBody User user){
        user.setPassword(MD5Util.MD5Encode(user.getPassword(),"utf-8"));
        userService.updateById(user);
        return ResultGenerator.genSuccessResult();
    }
    /*
        用户状态修改,给用户id，状态status
     */
    @PutMapping("/changeStatus")
    public Result changeUserStatus(@RequestBody User user){
        userService.updateById(user);
        return ResultGenerator.genSuccessResult();
    }
}
