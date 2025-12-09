<template>
  <div class="container mx-auto p-4">
    <h2 class="text-2xl font-bold mb-4">角色管理</h2>
    
    <div class="flex justify-between items-center mb-4">
      <div class="flex space-x-4">
        <el-input v-model="searchQuery" placeholder="搜索角色名称" clearable />
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>
      <el-button 
        v-if="authStore.hasPermission('create', 'system')"
        type="success" 
        @click="handleAdd"
      >
        新增角色
      </el-button>
    </div>
    
    <el-table :data="roles" style="width: 100%">
      <el-table-column prop="id" label="角色ID" width="100" />
      <el-table-column prop="name" label="角色名称" width="180" />
      <el-table-column prop="code" label="角色编码" width="180" />
      <el-table-column prop="description" label="角色描述" />
      <el-table-column prop="createdAt" label="创建时间" width="180" />
      <el-table-column prop="updatedAt" label="更新时间" width="180" />
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="scope">
          <el-button 
            v-if="authStore.hasPermission('edit', 'system')"
            type="primary" 
            size="small" 
            @click="handleEdit(scope.row)"
          >
            编辑
          </el-button>
          <el-button 
            v-if="authStore.hasPermission('edit', 'system')"
            type="warning" 
            size="small" 
            @click="handlePermissions(scope.row)"
          >
            权限设置
          </el-button>
          <el-button 
            v-if="authStore.hasPermission('delete', 'system')"
            type="danger" 
            size="small" 
            @click="handleDelete(scope.row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <div class="mt-4 flex justify-center">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      />
    </div>
    
    <!-- 新增/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
    >
      <el-form :model="formData" label-width="120px">
        <el-form-item label="角色名称" required>
          <el-input v-model="formData.name" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="角色编码" required>
          <el-input v-model="formData.code" placeholder="请输入角色编码" />
        </el-form-item>
        <el-form-item label="角色描述">
          <el-input v-model="formData.description" type="textarea" placeholder="请输入角色描述" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
    
    <!-- 权限设置对话框 -->
    <el-dialog
      v-model="permissionsVisible"
      title="权限设置"
      width="800px"
    >
      <el-tree
        :data="permissionTree"
        show-checkbox
        node-key="id"
        default-expand-all
        :default-checked-keys="defaultCheckedKeys"
        @check="handlePermissionCheck"
      >
        <template #default="{ node, data }">
          <span class="flex items-center">
            <span>{{ node.label }}</span>
          </span>
        </template>
      </el-tree>
      <template #footer>
        <el-button @click="permissionsVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSavePermissions">保存权限</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useAuthStore } from '../../stores/auth'

const authStore = useAuthStore()

// 搜索
const searchQuery = ref('')

// 本地存储相关函数
const loadFromLocalStorage = () => {
  const storedData = localStorage.getItem('roleManagement')
  if (storedData) {
    return JSON.parse(storedData)
  }
  // 默认角色数据
  return [
    { 
      id: 1, 
      name: '系统管理员', 
      code: 'admin', 
      description: '拥有系统全部权限', 
      createdAt: '2025-01-01 00:00:00', 
      updatedAt: '2025-01-01 00:00:00' 
    },
    { 
      id: 2, 
      name: '生产管理员', 
      code: 'production_manager', 
      description: '管理生产相关功能', 
      createdAt: '2025-01-01 00:00:00', 
      updatedAt: '2025-01-01 00:00:00' 
    },
    { 
      id: 3, 
      name: '质量管理员', 
      code: 'quality_manager', 
      description: '管理质量相关功能', 
      createdAt: '2025-01-01 00:00:00', 
      updatedAt: '2025-01-01 00:00:00' 
    },
    { 
      id: 4, 
      name: '设备管理员', 
      code: 'equipment_manager', 
      description: '管理设备相关功能', 
      createdAt: '2025-01-01 00:00:00', 
      updatedAt: '2025-01-01 00:00:00' 
    },
    { 
      id: 5, 
      name: '普通用户', 
      code: 'user', 
      description: '拥有基础操作权限', 
      createdAt: '2025-01-01 00:00:00', 
      updatedAt: '2025-01-01 00:00:00' 
    }
  ]
}

const saveToLocalStorage = (data) => {
  localStorage.setItem('roleManagement', JSON.stringify(data))
}

// 原始角色数据
const originalRoles = ref(loadFromLocalStorage())
// 表格数据
const roles = ref([...originalRoles.value])

// 分页
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(roles.value.length)

// 对话框
const dialogVisible = ref(false)
const dialogTitle = ref('新增角色')
const formData = reactive({
  id: '',
  name: '',
  code: '',
  description: ''
})

// 权限设置
const permissionsVisible = ref(false)
const selectedRole = ref(null)
const defaultCheckedKeys = ref([])

// 权限树数据
const permissionTree = ref([
  {
    id: 1,
    label: '系统管理',
    children: [
      { id: 11, label: '用户管理' },
      { id: 12, label: '角色管理' },
      { id: 13, label: '权限管理' },
      { id: 14, label: '系统配置' }
    ]
  },
  {
    id: 2,
    label: '生产管理',
    children: [
      { id: 21, label: '生产计划' },
      { id: 22, label: '生产执行' },
      { id: 23, label: '生产统计' }
    ]
  },
  {
    id: 3,
    label: '质量管理',
    children: [
      { id: 31, label: '质量检查' },
      { id: 32, label: '不合格品管理' },
      { id: 33, label: '质量统计' }
    ]
  },
  {
    id: 4,
    label: '设备管理',
    children: [
      { id: 41, label: '设备列表' },
      { id: 42, label: '设备监控' },
      { id: 43, label: '维护记录' }
    ]
  },
  {
    id: 5,
    label: '数据分析',
    children: [
      { id: 51, label: '生产报表' },
      { id: 52, label: '质量报表' },
      { id: 53, label: '设备报表' }
    ]
  }
])

// 搜索
const handleSearch = () => {
  if (!searchQuery.value) {
    // 恢复原始数据
    roles.value = [...originalRoles.value]
  } else {
    // 过滤数据
    const filtered = originalRoles.value.filter(role => 
      role.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      role.code.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
    roles.value = filtered
  }
  total.value = roles.value.length
  currentPage.value = 1 // 重置到第一页
  ElMessage.success('搜索完成，找到 ' + roles.value.length + ' 条记录')
}

// 新增
const handleAdd = () => {
  if (!authStore.hasPermission('create', 'system')) {
    ElMessage.warning('您没有创建权限')
    return
  }
  
  dialogTitle.value = '新增角色'
  Object.assign(formData, {
    id: '',
    name: '',
    code: '',
    description: ''
  })
  dialogVisible.value = true
}

// 编辑
const handleEdit = (row) => {
  if (!authStore.hasPermission('edit', 'system')) {
    ElMessage.warning('您没有编辑权限')
    return
  }
  
  dialogTitle.value = '编辑角色'
  Object.assign(formData, { ...row })
  dialogVisible.value = true
}

// 删除
const handleDelete = (row) => {
  if (!authStore.hasPermission('delete', 'system')) {
    ElMessage.warning('您没有删除权限')
    return
  }
  
  ElMessageBox.confirm(
      `确定要删除角色「${row.name}」吗？`, 
      '确认删除', 
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    ).then(() => {
      // 更新原始数据
      originalRoles.value = originalRoles.value.filter(r => r.id !== row.id)
      // 更新表格数据
      roles.value = roles.value.filter(r => r.id !== row.id)
      total.value = roles.value.length
      // 保存到本地存储
      syncToLocalStorage()
      ElMessage.success('删除成功')
    }).catch(() => {
      ElMessage.info('已取消删除')
    })
}

// 提交
const handleSubmit = () => {
  // 表单验证
  if (!formData.name || !formData.code) {
    ElMessage.error('请填写角色名称和角色编码')
    return
  }
  
  const now = new Date().toISOString().slice(0, 19).replace('T', ' ')
  
  if (formData.id) {
    // 编辑
    const index = originalRoles.value.findIndex(r => r.id === formData.id)
    if (index !== -1) {
      originalRoles.value[index] = {
        ...formData,
        updatedAt: now
      }
      // 更新表格数据
      const tableIndex = roles.value.findIndex(r => r.id === formData.id)
      if (tableIndex !== -1) {
        roles.value[tableIndex] = { ...originalRoles.value[index] }
      }
      ElMessage.success('编辑成功')
    }
  } else {
    // 新增
    const newRole = {
      ...formData,
      id: Date.now(), // 生成唯一ID
      createdAt: now,
      updatedAt: now
    }
    originalRoles.value.unshift(newRole)
    // 如果是在搜索状态，只有当符合搜索条件时才添加到表格
    if (!searchQuery.value || 
        newRole.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
        newRole.code.toLowerCase().includes(searchQuery.value.toLowerCase())) {
      roles.value.unshift(newRole)
      total.value = roles.value.length
    }
    ElMessage.success('新增成功')
  }
  
  // 保存到本地存储
  syncToLocalStorage()
  dialogVisible.value = false
}

// 设置权限
const handlePermissions = (row) => {
  if (!authStore.hasPermission('edit', 'system')) {
    ElMessage.warning('您没有设置权限的权限')
    return
  }
  
  selectedRole.value = row
  // 这里可以根据角色获取实际的权限
  defaultCheckedKeys.value = [1, 11, 12, 2, 21, 22]
  permissionsVisible.value = true
}

// 权限检查
const handlePermissionCheck = (data, checked) => {
  // 这里可以处理权限检查逻辑
  console.log('权限检查:', data, checked)
}

// 保存权限
const handleSavePermissions = () => {
  // 模拟保存权限
  permissionsVisible.value = false
  ElMessage.success('权限保存成功')
}

// 在组件挂载时加载数据
onMounted(() => {
  originalRoles.value = loadFromLocalStorage()
  roles.value = [...originalRoles.value]
  total.value = roles.value.length
})

// 保存数据到本地存储的函数
const syncToLocalStorage = () => {
  saveToLocalStorage(originalRoles.value)
}
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
}
</style>