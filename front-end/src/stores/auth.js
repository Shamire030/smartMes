import { defineStore } from 'pinia'
import jwtDecode from 'jwt-decode'
import { loginApi, logoutApi } from '../api/auth'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || null,
    user: null,
    loading: false,
    error: null
  }),

  getters: {
    isAuthenticated: (state) => !!state.token,
    userInfo: (state) => state.user
  },

  actions: {
    async login(username, password) {
      this.loading = true
      this.error = null
      try {
        const response = await loginApi(username, password)
        const { token } = response.data
        localStorage.setItem('token', token)
        this.token = token
        this.user = jwtDecode(token)
        return true
      } catch (error) {
        this.error = error.response?.data?.message || '登录失败，请检查用户名和密码'
        return false
      } finally {
        this.loading = false
      }
    },

    async logout() {
      try {
        await logoutApi()
      } catch (error) {
        console.error('Logout failed:', error)
      } finally {
        localStorage.removeItem('token')
        this.token = null
        this.user = null
        this.error = null
      }
    },

    setToken(token) {
      localStorage.setItem('token', token)
      this.token = token
      this.user = jwtDecode(token)
    },

    clearToken() {
      localStorage.removeItem('token')
      this.token = null
      this.user = null
    },

    checkAuth() {
      const token = localStorage.getItem('token')
      if (token) {
        try {
          const decoded = jwtDecode(token)
          // 检查token是否过期
          if (decoded.exp < Date.now() / 1000) {
            this.clearToken()
            return false
          }
          this.token = token
          this.user = decoded
          return true
        } catch (error) {
          this.clearToken()
          return false
        }
      }
      return false
    }
  }
})