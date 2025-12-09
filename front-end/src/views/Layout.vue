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
        <el-menu-item index="/">
          <i class="el-icon-s-data"></i>
          <span>仪表盘</span>
        </el-menu-item>
        
        <el-sub-menu index="production">
          <template #title>
            <i class="el-icon-s-operation"></i>
            <span>生产管理</span>
          </template>
          <el-menu-item index="/production-plan">生产计划</el-menu-item>
          <el-menu-item index="/production-execution">生产执行</el-menu-item>
        </el-sub-menu>
        
        <el-sub-menu index="quality">
          <template #title>
            <i class="el-icon-s-check"></i>
            <span>质量管理</span>
          </template>
          <el-menu-item index="/quality-management">质检录入</el-menu-item>
        </el-sub-menu>
        
        <el-sub-menu index="equipment">
          <template #title>
            <i class="el-icon-s-tools"></i>
            <span>设备管理</span>
          </template>
          <el-menu-item index="/equipment-management">设备监控</el-menu-item>
        </el-sub-menu>
        
        <el-sub-menu index="system">
          <template #title>
            <i class="el-icon-s-setting"></i>
            <span>系统管理</span>
          </template>
          <el-menu-item index="/user-management">用户管理</el-menu-item>
          <el-menu-item index="/role-management">角色管理</el-menu-item>
        </el-sub-menu>
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
              <el-avatar :size="32" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
              <span>{{ authStore.user?.username || '未知用户' }}</span>
              <i class="el-icon-arrow-down el-icon--right"></i>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="handleProfile">
                  <i class="el-icon-user"></i>
                  <span>个人中心</span>
                </el-dropdown-item>
                <el-dropdown-item @click="handleSettings">
                  <i class="el-icon-setting"></i>
                  <span>设置</span>
                </el-dropdown-item>
                <el-dropdown-item divided @click="handleLogout">
                  <i class="el-icon-switch-button"></i>
                  <span>退出登录</span>
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
import { ref, computed, watch } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'
import { ElMessage } from 'element-plus'

const router = useRouter()
const authStore = useAuthStore()
const sidebarCollapsed = ref(false)

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

// 监听路由变化，处理菜单激活状态
watch(
  () => router.currentRoute.value.path,
  () => {
    // 路由变化时的逻辑
  }
)

// 切换侧边栏
const toggleSidebar = () => {
  sidebarCollapsed.value = !sidebarCollapsed.value
}

// 处理个人中心
const handleProfile = () => {
  ElMessage.info('个人中心功能开发中')
}

// 处理设置
const handleSettings = () => {
  ElMessage.info('设置功能开发中')
}

// 处理退出登录
const handleLogout = async () => {
  try {
    await authStore.logout()
    router.push('/login')
    ElMessage.success('退出登录成功')
  } catch (error) {
    ElMessage.error('退出登录失败')
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