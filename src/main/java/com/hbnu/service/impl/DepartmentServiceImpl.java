package com.hbnu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hbnu.entity.Department;
import com.hbnu.mapper.DepartmentMapper;
import com.hbnu.service.DepartmentService;
import com.hbnu.utils.QueryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public IPage<Department> queryDepartments(QueryRequest queryRequest, Department department) {
        IPage<Department> departmentIPage = new Page<>(queryRequest.getPageNum(),queryRequest.getPageSize(),true);
        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        departmentQueryWrapper.like(!ObjectUtils.isEmpty(department.getDep_name()),"dep_name",department.getDep_name())
        .like(!ObjectUtils.isEmpty(department.getLetter()),"letter",department.getLetter())
        .orderByDesc("create_time");
        return departmentMapper.selectPage(departmentIPage,departmentQueryWrapper);
    }
}
