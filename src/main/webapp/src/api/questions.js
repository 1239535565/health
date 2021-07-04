import request from '@/utils/request'

// 新增
export function ask(data) {
  return request({
    url: '/adminApi/questions/ask',
    method: 'post',
    data: data
  })
}

// 查询问答列表
export function listQuestions(query) {
  return request({
    url: '/adminApi/questions/list',
    method: 'get',
    params: query
  })
}



// 删除问答
export function delQuestions(id) {
  return request({
    url: '/adminApi/questions/' + id,
    method: 'delete'
  })
}

// 回答问题
export function reply(data) {
  return request({
    url: '/adminApi/questions/reply',
    method: 'put',
    data: data
  })
}

