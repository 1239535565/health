package com.hbnu.health;

import com.hbnu.entity.User;
import com.hbnu.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class HealthApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findAll(){
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

}
