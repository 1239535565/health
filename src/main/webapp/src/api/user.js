import request from '@/utils/request'

export function queryAll(query) {
  return request({
    url: '/adminApi/sysUser',
    method: 'get',
    params: query
  })
}

export function upt(data) {
  return request({
    url: '/adminApi/sysUser',
    method: 'put',
    data
  })
}

export function insert(data) {
  return request({
    url: '/adminApi/sysUser',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: '/adminApi/sysUser/' + id,
    method: 'delete',
    params: id
  })
}

export function queryUser() {
  return request({
    url: '/adminApi/sysUser',
    method: 'get'
  })
}
