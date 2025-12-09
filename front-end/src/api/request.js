import axios from 'axios'
import { useAuthStore } from '../stores/auth'

// 创建axios实例
const request = axios.create({
  baseURL: 'http://localhost:8081/smartmes/api',
  timeout: 10000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    const authStore = useAuthStore()
    if (authStore.token) {
      config.headers['Authorization'] = `Bearer ${authStore.token}`
    }
    return config
  },
  error => {
    console.error('Request error:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    return response
  },
  error => {
    const authStore = useAuthStore()
    
    // 处理401未授权错误
    if (error.response?.status === 401) {
      authStore.clearToken()
      // 跳转到登录页
      window.location.href = '/login'
    }
    
    // 处理其他错误
    console.error('Response error:', error)
    return Promise.reject(error)
  }
)

export default request