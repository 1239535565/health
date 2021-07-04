package com.hbnu.controller;

import com.hbnu.utils.QueryRequest;
import com.hbnu.utils.Result;
import com.hbnu.utils.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.hbnu.entity.Department;
import com.hbnu.service.DepartmentService;

import java.util.Arrays;

@RestController
@RequestMapping("/adminApi/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    /*
        查询医院科室列表,给参数
     */
    @GetMapping("/list")
    public Result listDepartment(QueryRequest queryRequest, Department department){
        return ResultGenerator.genSuccessResult(departmentService.queryDepartments(queryRequest,department));
    }

    /*
        查询医院科室详细，给id
     */
    @GetMapping("/{deptId}")
    public Result getDepartment(@PathVariable("deptId") Integer id){
        return ResultGenerator.genSuccessResult(departmentService.getById(id));
    }

    /*
        新增医院科室，给参数
     */
    @PostMapping
    public Result addDepartment(@RequestBody Department department){
        departmentService.save(department);
        return ResultGenerator.genSuccessResult();
    }

    /*
        修改医院科室，给参数
     */
    @PutMapping
    public String updateDepartment(){
        return "";
    }

    /*
        删除医院科室，给id
     */
    @DeleteMapping("/{deptIds}")
    public Result delDepartment(@PathVariable("deptIds") Integer[] deptIds){
        return ResultGenerator.genSuccessResult(departmentService.removeByIds(Arrays.asList(deptIds)));
    }




}
