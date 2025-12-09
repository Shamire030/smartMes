<template>
  <div class="layout-container">
    <!-- 侧边栏 -->
    <aside class="sidebar">
      <div class="sidebar-header">
        <i class="el-icon-s-cooperation sidebar-logo"></i>
        <span class="sidebar-title">Smart MES</span>
      </div>
      
      <el-menu
        :default-active="activeMenu"
        class="sidebar-menu"
        background-color="#0f172a"
        text-color="#cbd5e1"
        active-text-color="#3b82f6"
        router
        unique-opened
      >
        <template v-for="item in menuItems" :key="item.index">
          <!-- 普通菜单项 -->
          <el-menu-item v-if="!item.children" :index="item.index">
            <i :class="`el-icon-${item.icon}`"></i>
            <span>{{ item.label }}</span>
          </el-menu-item>
          
          <!-- 子菜单 -->
          <el-sub-menu v-else :index="item.index">
            <template #title>
              <i :class="`el-icon-${item.icon}`"></i>
              <span>{{ item.label }}</span>
            </template>
            <el-menu-item v-for="child in item.children" :key="child.index" :index="child.index">
              {{ child.label }}
            </el-menu-item>
          </el-sub-menu>
        </template>
      </el-menu>
    </aside>
    
    <!-- 主内容区 -->
    <main class="main-content">
      <!-- 顶部导航栏 -->
      <header class="top-nav">
        <div class="nav-left">
          <el-button type="text" @click="toggleSidebar">
            <i class="el-icon-menu"></i>
          </el-button>
        </div>
        
        <div class="nav-center">
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-for="item in breadcrumb" :key="item.path" :to="{ path: item.path }">
              {{ item.label }}
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        
        <div class="nav-right">
          <el-dropdown trigger="click">
            <span class="user-info">
              <el-avatar :size="32" :src="authStore.user?.avatar || 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'"></el-avatar>
              <span>{{ authStore.displayName }}</span>
              <i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item v-for="(option, index) in userMenuOptions" :key="index" 
                                  @click="option.action && handleUserMenuClick(option.action)" 
                                  :divided="option.type === 'divider'">
                  <i v-if="option.icon" :class="`el-icon-${option.icon}`"></i>
                  <span v-if="option.label">{{ option.label }}</span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </header>
      
      <!-- 内容区域 -->
      <div class="content-wrapper">
        <router-view />
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, computed, watch, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const authStore = useAuthStore()
const sidebarCollapsed = ref(false)
const menuItems = ref([])

// 计算当前激活的菜单
const activeMenu = computed(() => {
  return router.currentRoute.value.path
})

// 计算面包屑
const breadcrumb = computed(() => {
  const routes = router.currentRoute.value.matched
  const result = []
  
  routes.forEach(route => {
    if (route.meta && route.meta.title) {
      result.push({
        path: route.path,
        label: route.meta.title
      })
    }
  })
  
  return result
})

// 生成侧边栏菜单
const generateMenu = () => {
  const menus = [
    {
      index: '/',
      label: '仪表盘',
      icon: 's-data',
      page: 'dashboard'
    },
    {
      index: 'production',
      label: '生产管理',
      icon: 's-operation',
      page: 'production',
      children: [
        { index: '/production-plan', label: '生产计划' },
        { index: '/production-execution', label: '生产执行' }
      ]
    },
    {
      index: 'quality',
      label: '质量管理',
      icon: 's-check',
      page: 'quality',
      children: [
        { index: '/quality-management', label: '质检录入' }
      ]
    },
    {
      index: 'equipment',
      label: '设备管理',
      icon: 's-tools',
      page: 'equipment',
      children: [
        { index: '/equipment-management', label: '设备监控' }
      ]
    },
    {
      index: 'system',
      label: '系统管理',
      icon: 's-setting',
      page: 'system',
      children: [
        { index: '/user-management', label: '用户管理' },
        { index: '/role-management', label: '角色管理' }
      ]
    }
  ]

  // 根据权限过滤菜单
  return menus.filter(item => {
    // 检查是否有权限访问该页面
    if (item.page && !authStore.canAccessPage(item.page)) {
      return false
    }
    // 检查子菜单权限
    if (item.children) {
      item.children = item.children.filter(child => {
        // 子菜单权限检查（可根据需要扩展）
        return true
      })
      // 如果子菜单都被过滤掉，不显示该菜单
      return item.children.length > 0
    }
    return true
  })
}

// 监听路由变化，处理菜单激活状态
watch(
  () => router.currentRoute.value.path,
  () => {
    // 路由变化时的逻辑
  }
)

// 监听角色变化，更新菜单
watch(
  () => authStore.currentRole,
  () => {
    menuItems.value = generateMenu()
  }
)

// 初始化菜单
onMounted(() => {
  menuItems.value = generateMenu()
})

// 切换侧边栏
const toggleSidebar = () => {
  sidebarCollapsed.value = !sidebarCollapsed.value
}

// 用户下拉菜单选项
const userMenuOptions = computed(() => {
  const baseOptions = [
    {
      label: '用户信息',
      icon: 'user',
      action: 'profile'
    },
    {
      label: '系统设置',
      icon: 'setting',
      action: 'settings',
      show: authStore.isAdmin
    }
  ]

  // 角色切换选项
  const roleOptions = [
    {
      label: '切换到只读用户',
      icon: 'lock',
      action: 'switch_read_only',
      show: authStore.currentRole !== 'read_only'
    },
    {
      label: '切换到操作员',
      icon: 'user',
      action: 'switch_operator', 
      show: authStore.currentRole !== 'operator'
    },
    {
      label: '切换到管理员',
      icon: 's-custom',
      action: 'switch_admin',
      show: authStore.currentRole !== 'admin'
    },
    {
      label: '切换到超级管理员',
      icon: 'star',
      action: 'switch_super_admin',
      show: authStore.currentRole !== 'super_admin'
    }
  ].filter(option => option.show)

  return [...baseOptions, ...roleOptions, { type: 'divider' }, {
    label: '退出登录',
    icon: 'switch-button',
    action: 'logout'
  }]
})

// 处理用户菜单点击
const handleUserMenuClick = (action) => {
  switch (action) {
    case 'switch_read_only':
      authStore.setupDefaultUser('read_only')
      ElMessage.success('已切换到只读用户模式')
      break
    case 'switch_operator':
      authStore.setupDefaultUser('operator')
      ElMessage.success('已切换到操作员模式')
      break
    case 'switch_admin':
      authStore.setupDefaultUser('admin')
      ElMessage.success('已切换到管理员模式')
      break
    case 'switch_super_admin':
      authStore.setupDefaultUser('super_admin')
      ElMessage.success('已切换到超级管理员模式')
      break
    case 'profile':
      ElMessage.info('个人中心功能开发中')
      break
    case 'settings':
      if (authStore.hasPermission('system_config')) {
        router.push('/system/settings')
      } else {
        ElMessage.warning('权限不足')
      }
      break
    case 'logout':
      handleLogout()
      break
  }
}

// 处理退出登录
const handleLogout = async () => {
  try {
    await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    localStorage.removeItem('token')
    localStorage.removeItem('user_data')
    authStore.user = null
    authStore.token = null
    router.push('/login')
    ElMessage.success('退出登录成功')
  } catch (error) {
    // 用户取消
  }
}
</script>

<style scoped>
.layout-container {
  display: flex;
  height: 100vh;
  overflow: hidden;
}

/* 侧边栏 */
.sidebar {
  width: 250px;
  background-color: #0f172a;
  color: #cbd5e1;
  border-right: 1px solid #334155;
  transition: width 0.3s ease;
  overflow-y: auto;
}

.sidebar-header {
  display: flex;
  align-items: center;
  padding: 20px;
  border-bottom: 1px solid #334155;
}

.sidebar-logo {
  font-size: 24px;
  color: #3b82f6;
  margin-right: 10px;
}

.sidebar-title {
  font-size: 18px;
  font-weight: bold;
  color: #f1f5f9;
}

.sidebar-menu {
  border-right: none;
}

.sidebar-menu .el-menu-item {
  padding: 0 20px;
  height: 50px;
  line-height: 50px;
}

.sidebar-menu .el-sub-menu__title {
  padding: 0 20px;
  height: 50px;
  line-height: 50px;
}

/* 主内容区 */
.main-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  background-color: #f1f5f9;
  overflow: hidden;
}

/* 顶部导航栏 */
.top-nav {
  height: 60px;
  background-color: #ffffff;
  border-bottom: 1px solid #e2e8f0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
}

.nav-left .el-button {
  color: #475569;
}

.nav-center {
  flex: 1;
  display: flex;
  justify-content: center;
}

.nav-right .user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 5px 10px;
  border-radius: 4px;
  transition: background-color 0.2s;
}

.nav-right .user-info:hover {
  background-color: #f1f5f9;
}

.nav-right .user-info span {
  margin: 0 5px;
  color: #475569;
}

/* 内容区域 */
.content-wrapper {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background-color: #f8fafc;
}
</style>