package com.hbnu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hbnu.entity.User;
import com.hbnu.utils.QueryRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {
    IPage<User> queryDoctorUser(IPage<User> userIPage, @Param("queryRequest") QueryRequest queryRequest, @Param("user") User user);
}
