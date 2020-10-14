
export function add(data) {
  return request({
    url: 'api/abcd',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/abcd/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/abcd',
    method: 'put',
    data
  })
}

export default { add, edit, del }
