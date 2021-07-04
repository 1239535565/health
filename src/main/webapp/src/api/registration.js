import request from '@/utils/request'

// 新增
export function add(data) {
  return request({
    url: '/adminApi/registration',
    method: 'post',
    data: data
  })
}

// 查询挂号列表
export function listRegistration(query) {
  return request({
    url: '/adminApi/registration/list',
    method: 'get',
    params: query
  })
}

export function openInvoice(id) {
  return request({
    url: '/adminApi/registration/' + id,
    method: 'put'
  })
}

export function getInfoById(id) {
  return request({
    url: '/adminApi/registration/' + id,
    method: 'get'
  })
}

// 采纳
export function accept(id) {
  return request({
    url: '/adminApi/registration/accept/' + id,
    method: 'get'
  })
}

// 不采纳
export function notAccept(id) {
  return request({
    url: '/adminApi/registration/notAccept/' + id,
    method: 'get'
  })
}

// 评价
export function evaluate(id, evaluate) {
  const data = {
    id,
    evaluate
  }
  return request({
    url: '/adminApi/registration/evaluate/',
    method: 'put',
    data: data
  })
}

