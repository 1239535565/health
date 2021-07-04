import request from '@/utils/request'

// export function loginByUsername(query) {
//   return request({
//     url: '/adminApi/common/login',
//     method: 'POST',
//     params: query
//   })
// }

export function loginByUsername(username, password) {
  const data = {
    username,
    password
  }
  return request({
    url: '/adminApi/common/login',
    method: 'POST',
    params: data
  })
}

export function logout() {
  return request({
    url: '/adminApi/common/outLogin',
    method: 'GET'
  })
}

export function getUserInfo() {
  return request({
    url: '/adminApi/common/getUserInfo',
    method: 'GET'
  })
}




// 注册方法
export function registerPatients(query) {
  return request({
    url: '/adminApi/common/registerPatients',
    method: 'post',
    params: query
  })
}

// 注册方法
export function registerDoctor(query) {
  return request({
    url: '/adminApi/common/registerDoctor',
    method: 'post',
    params: query
  })
}

// 忘记密码
export function forgotPassword(query) {
  return request({
    url: '/adminApi/common/forgotPassword',
    method: 'put',
    params: query
  })
}