package com.hbnu.controller;

import com.hbnu.entity.Questions;
import com.hbnu.service.QuestionsService;
import com.hbnu.utils.Result;
import com.hbnu.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/adminApi/questions")
public class QuestionsController {

    @Autowired
    private QuestionsService questionsService;

    /*
        查询问答列表，给参数
     */
    @GetMapping("/list")
    @ResponseBody
    public Result queList(){
        List<Questions> list = questionsService.list();
        return ResultGenerator.genSuccessResult(list);
    }

    /*
        新增问答,给参数
     */
    @PostMapping("/ask")
    @ResponseBody
    public String ask(){
        return "";
    }

    /*
        删除问答,给id
     */
    @DeleteMapping("/")
    @ResponseBody
    public String delQuestions(Integer id){
        return "";
    }

    /*
        回复问题,给参数
     */
    @DeleteMapping("/reply")
    @ResponseBody
    public String reply(){
        return "";
    }



}
