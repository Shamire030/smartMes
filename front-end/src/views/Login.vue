<template>
  <div class="login-container">
    <el-card class="login-card">
      <template #header>
        <div class="login-title">
          <i class="el-icon-s-cooperation"></i>
          <span>Smart MES系统登录</span>
        </div>
      </template>
      
      <el-form ref="loginForm" :model="loginForm" :rules="rules" label-position="left">
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入用户名"
            prefix-icon="el-icon-user"
            clearable
            @keyup.enter="handleLogin"
          />
        </el-form-item>
        
        <el-form-item label="密码" prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            prefix-icon="el-icon-lock"
            show-password
            @keyup.enter="handleLogin"
          />
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" class="login-btn" :loading="authStore.loading" @click="handleLogin">
            登录
          </el-button>
        </el-form-item>
        
        <div v-if="authStore.error" class="login-error">
          <i class="el-icon-error"></i>
          <span>{{ authStore.error }}</span>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const router = useRouter()
const authStore = useAuthStore()
const loginForm = ref({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }, { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }]
}

const handleLogin = async () => {
  const success = await authStore.login(loginForm.value.username, loginForm.value.password)
  if (success) {
    router.push('/')
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  width: 400px;
  border-radius: 8px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
}

.login-title {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  font-weight: bold;
}

.login-title i {
  margin-right: 8px;
  font-size: 24px;
  color: #67c23a;
}

.login-btn {
  width: 100%;
  height: 40px;
  font-size: 16px;
}

.login-error {
  display: flex;
  align-items: center;
  justify-content: center;
  color: #f56c6c;
  margin-top: 10px;
}

.login-error i {
  margin-right: 5px;
}
</style>