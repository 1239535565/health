package com.hbnu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbnu.entity.Questions;
import com.hbnu.mapper.QuestionsMapper;
import com.hbnu.service.QuestionsService;
import org.springframework.stereotype.Service;


@Service
public class QuestionsServiceImpl extends ServiceImpl<QuestionsMapper, Questions> implements QuestionsService {
}
