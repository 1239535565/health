import request from '@/utils/request'

// 查询医院科室列表
export function listDepartment(query) {
  return request({
    url: '/adminApi/department/list',
    method: 'get',
    params: query
  })
}

// 查询医院科室详细
export function getDepartment(id) {
  return request({
    url: '/adminApi/department/' + id,
    method: 'get'
  })
}

// 新增医院科室
export function addDepartment(data) {
  return request({
    url: '/adminApi/department',
    method: 'post',
    data: data
  })
}

// 修改医院科室
export function updateDepartment(data) {
  return request({
    url: '/adminApi/department',
    method: 'put',
    data: data
  })
}

// 删除医院科室
export function delDepartment(id) {
  return request({
    url: '/adminApi/department/' + id,
    method: 'delete'
  })
}
