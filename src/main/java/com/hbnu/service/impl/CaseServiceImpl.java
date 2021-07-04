package com.hbnu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbnu.entity.Case;
import com.hbnu.mapper.CaseMapper;
import com.hbnu.service.CaseService;
import org.springframework.stereotype.Service;



@Service
public class CaseServiceImpl extends ServiceImpl<CaseMapper, Case> implements CaseService {
}
