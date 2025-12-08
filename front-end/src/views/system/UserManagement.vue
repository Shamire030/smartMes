<template>
  <div class="user-management-container">
    <!-- 页面头部 -->
    <div class="page-header">
      <h2><i class="el-icon-user"></i> 用户管理</h2>
      <div class="header-actions">
        <el-input
          v-model="searchQuery"
          placeholder="搜索用户名或邮箱"
          prefix-icon="el-icon-search"
          clearable
          style="width: 240px; margin-right: 16px;"
        />
        <el-button type="primary" @click="handleAddUser">
          <i class="el-icon-plus"></i> 新增用户
        </el-button>
      </div>
    </div>
    
    <!-- 数据表格 -->
    <el-card shadow="hover" class="table-card">
      <el-table :data="filteredUsers" style="width: 100%" stripe>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="ID" width="80" sortable></el-table-column>
        <el-table-column prop="username" label="用户名" width="150"></el-table-column>
        <el-table-column prop="email" label="邮箱" width="200"></el-table-column>
        <el-table-column prop="realName" label="真实姓名" width="120"></el-table-column>
        <el-table-column prop="role" label="角色" width="120">
          <template #default="scope">
            <el-tag :type="getRoleType(scope.row.role)">{{ scope.row.role }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="120">
          <template #default="scope">
            <el-switch
              v-model="scope.row.status"
              active-color="#67c23a"
              inactive-color="#f56c6c"
              :active-value="1"
              :inactive-value="0"
              @change="handleStatusChange(scope.row)"
            >
              <template #active>启用</template>
              <template #inactive>禁用</template>
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" sortable></el-table-column>
        <el-table-column prop="lastLoginTime" label="最后登录时间" width="180"></el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <el-button type="primary" size="small" @click="handleEditUser(scope.row)">
              <i class="el-icon-edit"></i> 编辑
            </el-button>
            <el-button type="warning" size="small" @click="handleResetPassword(scope.row)">
              <i class="el-icon-refresh"></i> 重置密码
            </el-button>
            <el-button type="danger" size="small" @click="handleDeleteUser(scope.row)">
              <i class="el-icon-delete"></i> 删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <!-- 分页 -->
      <div class="pagination-container">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="filteredUsers.length"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
    
    <!-- 新增/编辑用户对话框 -->
    <el-dialog
      v-model="userDialogVisible"
      :title="isEdit ? '编辑用户' : '新增用户'"
      width="500px"
      :close-on-click-modal="false"
    >
      <el-form ref="userForm" :model="userForm" :rules="rules" label-position="right" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userForm.username" placeholder="请输入用户名" clearable></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="userForm.realName" placeholder="请输入真实姓名" clearable></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="userForm.email" placeholder="请输入邮箱" clearable></el-input>
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="userForm.role" placeholder="请选择角色" clearable>
            <el-option label="管理员" value="admin"></el-option>
            <el-option label="生产主管" value="productionManager"></el-option>
            <el-option label="质检员" value="inspector"></el-option>
            <el-option label="操作员" value="operator"></el-option>
            <el-option label="普通用户" value="user"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item v-if="!isEdit" label="密码" prop="password">
          <el-input v-model="userForm.password" type="password" placeholder="请输入密码" show-password></el-input>
        </el-form-item>
        <el-form-item v-if="!isEdit" label="确认密码" prop="confirmPassword">
          <el-input v-model="userForm.confirmPassword" type="password" placeholder="请确认密码" show-password></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch
            v-model="userForm.status"
            active-color="#67c23a"
            inactive-color="#f56c6c"
            :active-value="1"
            :inactive-value="0"
          >
            <template #active>启用</template>
            <template #inactive>禁用</template>
          </el-switch>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="userDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmitUserForm">确定</el-button>
        </div>
      </template>
    </el-dialog>
    
    <!-- 重置密码对话框 -->
    <el-dialog
      v-model="resetPasswordDialogVisible"
      title="重置密码"
      width="400px"
      :close-on-click-modal="false"
    >
      <el-form ref="resetPasswordForm" :model="resetPasswordForm" :rules="resetPasswordRules" label-position="right" label-width="100px">
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="resetPasswordForm.newPassword" type="password" placeholder="请输入新密码" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="resetPasswordForm.confirmPassword" type="password" placeholder="请确认密码" show-password></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="resetPasswordDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleResetPasswordSubmit">确定</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

// 搜索查询
const searchQuery = ref('')

// 分页
const currentPage = ref(1)
const pageSize = ref(20)

// 用户数据
const users = ref([])

// 对话框
const userDialogVisible = ref(false)
const resetPasswordDialogVisible = ref(false)
const isEdit = ref(false)

// 表单数据
const userForm = ref({
  id: '',
  username: '',
  realName: '',
  email: '',
  role: '',
  password: '',
  confirmPassword: '',
  status: 1
})

const resetPasswordForm = ref({
  userId: '',
  newPassword: '',
  confirmPassword: ''
})

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  realName: [
    { required: true, message: '请输入真实姓名', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ],
  role: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于 6 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const resetPasswordRules = {
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于 6 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    { validator: validateResetConfirmPassword, trigger: 'blur' }
  ]
}

// 确认密码验证
const validateConfirmPassword = (rule, value, callback) => {
  if (value !== userForm.value.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const validateResetConfirmPassword = (rule, value, callback) => {
  if (value !== resetPasswordForm.value.newPassword) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

// 过滤后的用户列表
const filteredUsers = computed(() => {
  if (!searchQuery.value) {
    return users.value
  }
  return users.value.filter(user => {
    return (
      user.username.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      user.email.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  })
})

// 分页处理
const handleSizeChange = (newSize) => {
  pageSize.value = newSize
  currentPage.value = 1
}

const handleCurrentChange = (newPage) => {
  currentPage.value = newPage
}

// 获取角色类型
const getRoleType = (role) => {
  const roleMap = {
    admin: 'warning',
    productionManager: 'primary',
    inspector: 'success',
    operator: 'info',
    user: 'default'
  }
  return roleMap[role] || 'default'
}

// 新增用户
const handleAddUser = () => {
  isEdit.value = false
  userForm.value = {
    id: '',
    username: '',
    realName: '',
    email: '',
    role: '',
    password: '',
    confirmPassword: '',
    status: 1
  }
  userDialogVisible.value = true
}

// 编辑用户
const handleEditUser = (user) => {
  isEdit.value = true
  userForm.value = JSON.parse(JSON.stringify(user))
  userForm.value.password = ''
  userForm.value.confirmPassword = ''
  userDialogVisible.value = true
}

// 提交用户表单
const handleSubmitUserForm = () => {
  // 表单验证
  if (!userForm.value.username || !userForm.value.email || !userForm.value.role) {
    ElMessage.error('请填写必填字段')
    return
  }
  
  if (!isEdit.value && (!userForm.value.password || userForm.value.password.length < 6)) {
    ElMessage.error('密码长度不能少于 6 个字符')
    return
  }
  
  if (!isEdit.value && userForm.value.password !== userForm.value.confirmPassword) {
    ElMessage.error('两次输入的密码不一致')
    return
  }
  
  if (isEdit.value) {
    // 编辑用户逻辑
    const index = users.value.findIndex(u => u.id === userForm.value.id)
    if (index !== -1) {
      users.value[index] = { ...userForm.value }
    }
    ElMessage.success('用户编辑成功')
  } else {
    // 新增用户逻辑
    const newUser = {
      ...userForm.value,
      id: Date.now(),
      createTime: new Date().toISOString().slice(0, 19).replace('T', ' '),
      lastLoginTime: null
    }
    users.value.unshift(newUser)
    ElMessage.success('用户新增成功')
  }
  
  userDialogVisible.value = false
}

// 重置密码
const handleResetPassword = (user) => {
  resetPasswordForm.value = {
    userId: user.id,
    newPassword: '',
    confirmPassword: ''
  }
  resetPasswordDialogVisible.value = true
}

// 提交重置密码
const handleResetPasswordSubmit = () => {
  // 表单验证
  if (!resetPasswordForm.value.newPassword || resetPasswordForm.value.newPassword.length < 6) {
    ElMessage.error('密码长度不能少于 6 个字符')
    return
  }
  
  if (resetPasswordForm.value.newPassword !== resetPasswordForm.value.confirmPassword) {
    ElMessage.error('两次输入的密码不一致')
    return
  }
  
  // 重置密码逻辑
  const index = users.value.findIndex(u => u.id === resetPasswordForm.value.userId)
  if (index !== -1) {
    users.value[index].password = resetPasswordForm.value.newPassword // 实际项目中应该加密存储
  }
  
  ElMessage.success('密码重置成功')
  resetPasswordDialogVisible.value = false
}

// 删除用户
const handleDeleteUser = (user) => {
  ElMessageBox.confirm(`确定要删除用户「${user.username}」吗？`, '删除用户', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 删除用户逻辑
    const index = users.value.findIndex(u => u.id === user.id)
    if (index !== -1) {
      users.value.splice(index, 1)
    }
    ElMessage.success('用户删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 状态变更
const handleStatusChange = (user) => {
  // 更新用户状态逻辑
  ElMessage.success(`用户「${user.username}」${user.status ? '已启用' : '已禁用'}`)
}

// 模拟数据
const initUsersData = () => {
  users.value = [
    {
      id: 1,
      username: 'admin',
      realName: '管理员',
      email: 'admin@example.com',
      role: 'admin',
      password: '123456',
      status: 1,
      createTime: '2025-03-01 08:00:00',
      lastLoginTime: '2025-03-20 09:30:00'
    },
    {
      id: 2,
      username: 'production_manager',
      realName: '生产主管',
      email: 'production@example.com',
      role: 'productionManager',
      password: '123456',
      status: 1,
      createTime: '2025-03-02 09:00:00',
      lastLoginTime: '2025-03-19 14:20:00'
    },
    {
      id: 3,
      username: 'inspector',
      realName: '质检员',
      email: 'inspector@example.com',
      role: 'inspector',
      password: '123456',
      status: 1,
      createTime: '2025-03-03 10:00:00',
      lastLoginTime: '2025-03-20 10:45:00'
    },
    {
      id: 4,
      username: 'operator',
      realName: '操作员',
      email: 'operator@example.com',
      role: 'operator',
      password: '123456',
      status: 1,
      createTime: '2025-03-04 11:00:00',
      lastLoginTime: '2025-03-20 08:15:00'
    },
    {
      id: 5,
      username: 'user1',
      realName: '普通用户',
      email: 'user1@example.com',
      role: 'user',
      password: '123456',
      status: 1,
      createTime: '2025-03-05 14:00:00',
      lastLoginTime: null
    }
  ]
}

onMounted(() => {
  initUsersData()
})
</script>

<style scoped>
.user-management-container {
  padding: 16px 0;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
  font-size: 20px;
  font-weight: bold;
  color: #1e293b;
}

.header-actions {
  display: flex;
  align-items: center;
}

.table-card {
  border-radius: 8px;
}

.pagination-container {
  display: flex;
  justify-content: flex-end;
  margin-top: 16px;
}

.dialog-footer {
  text-align: right;
}
</style>