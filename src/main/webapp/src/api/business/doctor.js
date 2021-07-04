import request from '@/utils/request'

// 查询用户列表
export function listUser(query) {
  return request({
    url: '/adminApi/doctor/list',
    method: 'get',
    params: query
  })
}

// 查询用户详细
export function getUser(userId) {
  return request({
    url: '/adminApi/doctor/' + userId,
    method: 'get'
  })
}

// 新增用户
export function addUser(data) {
  return request({
    url: '/adminApi/doctor',
    method: 'post',
    data: data
  })
}

// 修改用户
export function updateUser(data) {
  return request({
    url: '/adminApi/doctor',
    method: 'put',
    data: data
  })
}

// 删除用户
export function delUser(userId) {
  return request({
    url: '/adminApi/doctor/' + userId,
    method: 'delete'
  })
}


// 用户密码重置
export function resetUserPwd(userId, password,username) {
  const data = {
    userId,
    password,
    username
  }
  return request({
    url: '/adminApi/doctor/resetPwd',
    method: 'put',
    data: data
  })
}

// 用户状态修改
export function changeUserStatus(userId, status) {
  const data = {
    userId,
    status
  }
  return request({
    url: '/adminApi/doctor/changeStatus',
    method: 'put',
    data: data
  })
}

