import request from '@/utils/request'

// 开处方
export function makeCase(data) {
  return request({
    url: '/adminApi/case/makeCase',
    method: 'post',
    data: data
  })
}

// 查询列表
export function listCase(query) {
  return request({
    url: '/adminApi/case/list',
    method: 'get',
    params: query
  })
}
