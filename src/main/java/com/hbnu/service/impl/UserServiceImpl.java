package com.hbnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbnu.entity.User;
import com.hbnu.mapper.UserMapper;
import com.hbnu.service.UserService;
import com.hbnu.utils.QueryRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public IPage<User> queryAdminUser(QueryRequest queryRequest, User user) {
        IPage<User> userPage = new Page<>(queryRequest.getPageNum(), queryRequest.getPageSize(),true);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("role_id",0)
                .like(!ObjectUtils.isEmpty(user.getUsername()),"username",user.getUsername())
                .like(!ObjectUtils.isEmpty(user.getMobile()),"mobile",user.getMobile())
                .between(!ObjectUtils.isEmpty(queryRequest.getBeginTime())&&!ObjectUtils.isEmpty(queryRequest.getEndTime()),
                        "create_time",queryRequest.getBeginTime(),queryRequest.getEndTime())
                .orderByDesc("create_time");
        return userMapper.selectPage(userPage,userQueryWrapper);
    }

    @Override
    public IPage<User> queryDoctorUser(QueryRequest queryRequest, User user) {
        IPage<User> userPage = new Page<>(queryRequest.getPageNum(),queryRequest.getPageSize(),true);
        IPage<User> userIPage = userMapper.queryDoctorUser(userPage,queryRequest, user);
        return userIPage;
    }

    @Override
    public IPage<User> queryPatientsUser(QueryRequest queryRequest, User user) {
        IPage<User> userPage = new Page<>(queryRequest.getPageNum(), queryRequest.getPageSize(),true);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("role_id",2)
                .like(!ObjectUtils.isEmpty(user.getUsername()),"username",user.getUsername())
                .like(!ObjectUtils.isEmpty(user.getMobile()),"mobile",user.getMobile())
                .between(!ObjectUtils.isEmpty(queryRequest.getBeginTime())&&!ObjectUtils.isEmpty(queryRequest.getEndTime()),
                        "create_time",queryRequest.getBeginTime(),queryRequest.getEndTime())
                .orderByDesc("create_time");
        return userMapper.selectPage(userPage,userQueryWrapper);
    }
}
