import request from '@/utils/request'

// 表单校验 新增用户时验证库里是否有重复用户名
export function checkUsername(username) {
  return request({
    url: '/adminApi/common/checkUsername/' + username,
    method: 'get'
  })
}

// 表单校验 修改密码时验证旧密码是否正确
export function checkPassword(password) {
  return request({
    url: '/adminApi/common/checkPassword/' + password,
    method: 'get'
  })
}

// 获取科室下拉框
export function getDepartment() {
  return request({
    url: '/adminApi/common/getDepartment/',
    method: 'get'
  })
}


// 通过科室id查询医生
export function getDoctorById(id) {
  return request({
    url: '/adminApi/common/getDoctor/' + id,
    method: 'get'
  })
}

// 通过用户名查询医生详情
export function getDoctorInfoByUsername(username) {
  return request({
    url: '/adminApi/common/getDoctorInfoByUsername/' + username,
    method: 'get'
  })
}