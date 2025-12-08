import request from './request'

// 登录请求
export const loginApi = (username, password) => {
  return request.post('/auth/login', { username, password })
}

// 注销请求
export const logoutApi = () => {
  return request.post('/auth/logout')
}

// 获取用户信息
export const getUserInfoApi = () => {
  return request.get('/auth/user-info')
}

// 刷新token
export const refreshTokenApi = () => {
  return request.post('/auth/refresh-token')
}