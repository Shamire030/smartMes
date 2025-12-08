<template>
  <div class="container mx-auto p-4">
    <h2 class="text-2xl font-bold mb-4">角色管理</h2>
    
    <div class="flex justify-between items-center mb-4">
      <div class="flex space-x-4">
        <el-input v-model="searchQuery" placeholder="搜索角色名称" clearable />
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>
      <el-button type="success" @click="handleAdd">新增角色</el-button>
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
          <el-button type="primary" size="small" @click="handleEdit(scope.row)">
            编辑
          </el-button>
          <el-button type="warning" size="small" @click="handlePermissions(scope.row)">
            权限设置
          </el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope.row)">
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
import { ref, reactive } from 'vue'

// 搜索
const searchQuery = ref('')

// 表格数据
const roles = ref([
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
])

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
  // 这里可以添加实际的搜索逻辑
  console.log('搜索:', searchQuery.value)
}

// 新增
const handleAdd = () => {
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
  dialogTitle.value = '编辑角色'
  Object.assign(formData, { ...row })
  dialogVisible.value = true
}

// 删除
const handleDelete = (row) => {
  // 这里可以添加实际的删除逻辑
  console.log('删除角色:', row.id)
}

// 提交
const handleSubmit = () => {
  // 这里可以添加实际的提交逻辑
  dialogVisible.value = false
  console.log('提交:', formData)
}

// 设置权限
const handlePermissions = (row) => {
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
  // 这里可以添加实际的保存权限逻辑
  permissionsVisible.value = false
  console.log('保存权限:', selectedRole.value.id, defaultCheckedKeys.value)
}
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
}
</style>