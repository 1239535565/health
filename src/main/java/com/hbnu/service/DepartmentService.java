package com.hbnu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hbnu.entity.Department;
import com.hbnu.utils.QueryRequest;


public interface DepartmentService extends IService<Department> {
    IPage<Department> queryDepartments(QueryRequest queryRequest, Department department);
}
