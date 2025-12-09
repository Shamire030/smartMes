<template>
  <div class="permission-demo">
    <el-card>
      <template #header>
        <h2>🔐 权限演示页面</h2>
        <p>当前用户: {{ authStore.roleName }}</p>
      </template>

      <el-row :gutter="20">
        <el-col :span="8">
          <el-card>
            <template #header>
              <span>👤 用户信息</span>
            </template>
            <div class="user-info">
              <p><strong>角色:</strong> {{ authStore.roleName }}</p>
              <p><strong>权限级别:</strong> {{ authStore.permissionLevel }}</p>
            </div>
          </el-card>
        </el-col>

        <el-col :span="8">
          <el-card>
            <template #header>
              <span>🎯 权限测试</span>
            </template>
            <div class="permission-test">
              <el-button 
                :type="authStore.hasPermission('create') ? 'success' : 'info'"
                @click="testPermission('create')"
              >
                创建权限: {{ authStore.hasPermission('create') ? '✅' : '❌' }}
              </el-button>
              
              <el-button 
                :type="authStore.hasPermission('edit') ? 'warning' : 'info'"
                @click="testPermission('edit')"
                style="margin-top: 10px;"
              >
                编辑权限: {{ authStore.hasPermission('edit') ? '✅' : '❌' }}
              </el-button>
              
              <el-button 
                :type="authStore.hasPermission('delete') ? 'danger' : 'info'"
                @click="testPermission('delete')"
                style="margin-top: 10px;"
              >
                删除权限: {{ authStore.hasPermission('delete') ? '✅' : '❌' }}
              </el-button>
              
              <el-button 
                :type="authStore.hasPermission('export') ? 'primary' : 'info'"
                @click="testPermission('export')"
                style="margin-top: 10px;"
              >
                导出权限: {{ authStore.hasPermission('export') ? '✅' : '❌' }}
              </el-button>
            </div>
          </el-card>
        </el-col>

        <el-col :span="8">
          <el-card>
            <template #header>
              <span>📊 页面访问</span>
            </template>
            <div class="page-access">
              <el-tag 
                v-for="page in pages" 
                :key="page.name"
                :type="authStore.canAccessPage(page.key) ? 'success' : 'info'"
                style="margin: 5px;"
              >
                {{ page.name }}: {{ authStore.canAccessPage(page.key) ? '✅' : '❌' }}
              </el-tag>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <!-- 功能演示 -->
      <el-card style="margin-top: 20px;">
        <template #header>
          <span>🚀 功能演示</span>
        </template>
        
        <el-space wrap>
          <el-button 
            v-if="authStore.hasPermission('create')"
            type="primary" 
            @click="demoCreate"
          >
            新建功能（需要创建权限）
          </el-button>
          
          <el-button 
            v-if="authStore.hasPermission('edit')"
            type="warning" 
            @click="demoEdit"
          >
            编辑功能（需要编辑权限）
          </el-button>
          
          <el-button 
            v-if="authStore.hasPermission('delete')"
            type="danger" 
            @click="demoDelete"
          >
            删除功能（需要删除权限）
          </el-button>
          
          <el-button 
            v-if="authStore.hasPermission('export')"
            type="success" 
            @click="demoExport"
          >
            导出功能（需要导出权限）
          </el-button>
          
          <el-button 
            v-if="authStore.isSuperAdmin"
            type="warning" 
            @click="demoSuperAdmin"
          >
            超级管理员功能
          </el-button>
        </el-space>
      </el-card>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useAuthStore } from './stores/auth'

const authStore = useAuthStore()

const pages = ref([
  { name: '仪表盘', key: 'dashboard' },
  { name: '生产管理', key: 'production' },
  { name: '质量管理', key: 'quality' },
  { name: '设备管理', key: 'equipment' },
  { name: '系统管理', key: 'system' }
])

const testPermission = (permission) => {
  if (authStore.hasPermission(permission)) {
    ElMessage.success(`您拥有 ${permission} 权限`)
  } else {
    ElMessage.warning(`您没有 ${permission} 权限`)
  }
}

const demoCreate = () => {
  ElMessage.success('创建功能演示 - 只有操作员以上权限可见')
}

const demoEdit = () => {
  ElMessage.success('编辑功能演示 - 只有操作员以上权限可见')
}

const demoDelete = () => {
  ElMessage.success('删除功能演示 - 只有管理员以上权限可见')
}

const demoExport = () => {
  ElMessage.success('导出功能演示 - 只有管理员以上权限可见')
}

const demoSuperAdmin = () => {
  ElMessage.success('超级管理员专属功能演示')
}
</script>

<style scoped>
.permission-demo {
  padding: 20px;
}

.user-info p {
  margin: 8px 0;
}

.permission-test .el-button {
  width: 200px;
  justify-content: flex-start;
}

.page-access {
  display: flex;
  flex-wrap: wrap;
}
</style>
<template>
  <div class="permission-demo">
    <el-card>
      <template #header>
        <h2>🔐 权限演示页面</h2>
        <p>当前用户: {{ authStore.displayName }} ({{ authStore.roleName }})</p>
      </template>

      <el-row :gutter="20">
        <el-col :span="8">
          <el-card>
            <template #header>
              <span>👤 用户信息</span>
            </template>
            <div class="user-info">
              <p><strong>用户名:</strong> {{ authStore.user?.username }}</p>
              <p><strong>角色:</strong> {{ authStore.roleName }}</p>
              <p><strong>权限级别:</strong> {{ authStore.permissionLevel }}</p>
              <p><strong>邮箱:</strong> {{ authStore.user?.email }}</p>
            </div>
          </el-card>
        </el-col>

        <el-col :span="8">
          <el-card>
            <template #header>
              <span>🎯 权限测试</span>
            </template>
            <div class="permission-test">
              <el-button 
                :type="authStore.hasPermission('create') ? 'success' : 'info'"
                @click="testPermission('create')"
              >
                创建权限: {{ authStore.hasPermission('create') ? '✅' : '❌' }}
              </el-button>
              
              <el-button 
                :type="authStore.hasPermission('edit') ? 'warning' : 'info'"
                @click="testPermission('edit')"
                style="margin-top: 10px;"
              >
                编辑权限: {{ authStore.hasPermission('edit') ? '✅' : '❌' }}
              </el-button>
              
              <el-button 
                :type="authStore.hasPermission('delete') ? 'danger' : 'info'"
                @click="testPermission('delete')"
                style="margin-top: 10px;"
              >
                删除权限: {{ authStore.hasPermission('delete') ? '✅' : '❌' }}
              </el-button>
              
              <el-button 
                :type="authStore.hasPermission('export') ? 'primary' : 'info'"
                @click="testPermission('export')"
                style="margin-top: 10px;"
              >
                导出权限: {{ authStore.hasPermission('export') ? '✅' : '❌' }}
              </el-button>
            </div>
          </el-card>
        </el-col>

        <el-col :span="8">
          <el-card>
            <template #header>
              <span>📊 页面访问</span>
            </template>
            <div class="page-access">
              <el-tag 
                v-for="page in pages" 
                :key="page.name"
                :type="authStore.canAccessPage(page.key) ? 'success' : 'info'"
                style="margin: 5px;"
              >
                {{ page.name }}: {{ authStore.canAccessPage(page.key) ? '✅' : '❌' }}
              </el-tag>
            </div>
          </el-card>
        </el-col>
      </el-row>

      <!-- 功能演示 -->
      <el-card style="margin-top: 20px;">
        <template #header>
          <span>🚀 功能演示</span>
        </template>
        
        <el-space wrap>
          <el-button 
            v-if="authStore.hasPermission('create')"
            type="primary" 
            icon="el-icon-plus"
            @click="demoCreate"
          >
            新建功能（需要创建权限）
          </el-button>
          
          <el-button 
            v-if="authStore.hasPermission('edit')"
            type="warning" 
            icon="el-icon-edit"
            @click="demoEdit"
          >
            编辑功能（需要编辑权限）
          </el-button>
          
          <el-button 
            v-if="authStore.hasPermission('delete')"
            type="danger" 
            icon="el-icon-delete"
            @click="demoDelete"
          >
            删除功能（需要删除权限）
          </el-button>
          
          <el-button 
            v-if="authStore.hasPermission('export')"
            type="success" 
            icon="el-icon-download"
            @click="demoExport"
          >
            导出功能（需要导出权限）
          </el-button>
          
          <el-button 
            v-if="authStore.isSuperAdmin"
            type="warning" 
            icon="el-icon-star"
            @click="demoSuperAdmin"
          >
            超级管理员功能
          </el-button>
        </el-space>
      </el-card>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '../stores/auth'

const authStore = useAuthStore()

const pages = ref([
  { name: '仪表盘', key: 'dashboard' },
  { name: '生产管理', key: 'production' },
  { name: '质量管理', key: 'quality' },
  { name: '设备管理', key: 'equipment' },
  { name: '系统管理', key: 'system' }
])

const testPermission = (permission) => {
  if (authStore.hasPermission(permission)) {
    ElMessage.success(`您拥有 ${permission} 权限`)
  } else {
    ElMessage.warning(`您没有 ${permission} 权限`)
  }
}

const demoCreate = () => {
  ElMessage.success('创建功能演示 - 只有操作员以上权限可见')
}

const demoEdit = () => {
  ElMessage.success('编辑功能演示 - 只有操作员以上权限可见')
}

const demoDelete = () => {
  ElMessage.success('删除功能演示 - 只有管理员以上权限可见')
}

const demoExport = () => {
  ElMessage.success('导出功能演示 - 只有管理员以上权限可见')
}

const demoSuperAdmin = () => {
  ElMessage.success('超级管理员专属功能演示')
}
</script>

<style scoped>
.permission-demo {
  padding: 20px;
}

.user-info p {
  margin: 8px 0;
}

.permission-test .el-button {
  width: 200px;
  justify-content: flex-start;
}

.page-access {
  display: flex;
  flex-wrap: wrap;
}
</style>