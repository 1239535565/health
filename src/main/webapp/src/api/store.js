import request from '@/utils/request'

export function queryAll() {
  return request({
    url: '/adminApi/store',
    method: 'get'
  })
}

export function upt(data) {
  return request({
    url: '/adminApi/store',
    method: 'put',
    data
  })
}
