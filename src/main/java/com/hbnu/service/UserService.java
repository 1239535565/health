package com.hbnu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hbnu.entity.User;
import com.hbnu.utils.QueryRequest;


public interface UserService extends IService<User> {
    IPage<User> queryAdminUser(QueryRequest queryRequest, User user);
    IPage<User> queryDoctorUser(QueryRequest queryRequest, User user);
    IPage<User> queryPatientsUser(QueryRequest queryRequest, User user);
}
