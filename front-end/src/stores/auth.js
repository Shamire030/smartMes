import { defineStore } from 'pinia'
import { jwtDecode } from 'jwt-decode'
import { loginApi, logoutApi } from '../api/auth'
// 导入权限配置
import { PERMISSION_CONFIG, hasPermission, canAccessPage } from '../config/permissions'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: localStorage.getItem('token') || null,
    user: null,
    currentRole: 'read_only',
    loading: false,
    error: null
  }),

  getters: {
    isAuthenticated: (state) => !!state.token,
    userInfo: (state) => state.user,
    // 用户显示名称
    displayName: (state) => state.user?.name || '未知用户',
    // 用户角色名称
    roleName: (state) => {
      try {
        const roleConfig = PERMISSION_CONFIG.roles[state.currentRole]
        return roleConfig?.name || state.user?.role || '未知角色'
      } catch (error) {
        return state.user?.role || '未知角色'
      }
    },
    // 用户权限级别
    permissionLevel: (state) => {
      try {
        const roleConfig = PERMISSION_CONFIG.roles[state.currentRole]
        return roleConfig?.level || 1
      } catch (error) {
        return 1
      }
    },
    // 是否超级管理员
    isSuperAdmin: (state) => state.currentRole === 'super_admin',
    // 是否管理员以上
    isAdmin: (state) => ['admin', 'super_admin'].includes(state.currentRole)
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
      } else {
        // 使用默认只读用户（绕过登录）
        this.setupDefaultUser()
        return true
      }
    },

    // 设置默认用户（支持不同角色）
    setupDefaultUser(role = 'read_only') {
      const roleConfig = PERMISSION_CONFIG.roles[role]
      
      // 根据角色生成不同的用户数据
      const userTemplates = {
        read_only: {
          id: 1001,
          username: 'readonly_user',
          name: '只读用户',
          role: 'read_only',
          email: 'readonly@smartmes.com',
          avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=readonly'
        },
        operator: {
          id: 1002,
          username: 'operator',
          name: '操作员',
          role: 'operator',
          email: 'operator@smartmes.com',
          avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=operator'
        },
        admin: {
          id: 1003,
          username: 'admin',
          name: '管理员',
          role: 'admin',
          email: 'admin@smartmes.com',
          avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=admin'
        },
        super_admin: {
          id: 9999,
          username: 'superadmin',
          name: '超级管理员',
          role: 'super_admin',
          email: 'superadmin@smartmes.com',
          avatar: 'https://api.dicebear.com/7.x/avataaars/svg?seed=superadmin'
        }
      }

      this.user = userTemplates[role] || userTemplates.read_only
      this.currentRole = role
      this.token = `${role}_token_${Date.now()}`
      
      // 保存到localStorage
      localStorage.setItem('token', this.token)
      localStorage.setItem('user_data', JSON.stringify(this.user))
      
      console.log(`✅ ${roleConfig.name}已登录`)
    },

    // 切换到超级用户
    switchToAdmin() {
      this.setupDefaultUser('admin')
    },

    // 切换到只读用户
    switchToReadOnly() {
      this.setupDefaultUser('read_only')
    },

    // 权限检查方法
    hasPermission(permission, page = '') {
      try {
        return hasPermission(this.currentRole, permission, page)
      } catch (error) {
        console.warn('权限检查失败:', error)
        // 默认返回false确保安全
        return false
      }
    },

    // 页面访问检查
    canAccessPage(page) {
      try {
        return canAccessPage(this.currentRole, page)
      } catch (error) {
        console.warn('页面访问检查失败:', error)
        // 默认返回false确保安全
        return false
      }
    },

    // 获取用户角色信息
    getUserRoleInfo() {
      try {
        return PERMISSION_CONFIG.roles[this.currentRole]
      } catch (error) {
        return { name: this.user?.role, level: 1 }
      }
    }
  }
})