<template>
  <div class="not-found-container">
    <div class="not-found-content">
      <div class="error-code">404</div>
      <div class="error-message">页面未找到</div>
      <p class="error-description">
        抱歉，您访问的页面不存在或已被删除。
      </p>
      <el-button type="primary" @click="handleGoHome">
        <i class="el-icon-home"></i> 返回首页
      </el-button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const handleGoHome = () => {
  router.push('/')
}
</script>

<style scoped>
.not-found-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background-color: #f8fafc;
}

.not-found-content {
  text-align: center;
  padding: 40px;
  background-color: #ffffff;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.error-code {
  font-size: 96px;
  font-weight: bold;
  color: #3b82f6;
  margin-bottom: 16px;
}

.error-message {
  font-size: 24px;
  font-weight: bold;
  color: #1e293b;
  margin-bottom: 12px;
}

.error-description {
  color: #64748b;
  margin-bottom: 32px;
  font-size: 16px;
}
</style>