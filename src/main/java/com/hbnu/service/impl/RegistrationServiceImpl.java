package com.hbnu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbnu.entity.Registration;
import com.hbnu.mapper.RegistrationMapper;
import com.hbnu.service.RegistrationService;
import org.springframework.stereotype.Service;



@Service
public class RegistrationServiceImpl extends ServiceImpl<RegistrationMapper, Registration> implements RegistrationService {
}
