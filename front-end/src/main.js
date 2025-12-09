import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router'
import { useAuthStore } from './stores/auth'
import './style.css'
// 导入权限指令
import { permissionDirective } from './directives/permission'
import App from './App.vue'

const app = createApp(App)
const pinia = createPinia()

app.use(pinia)
app.use(ElementPlus)
app.use(router)

// 注册权限指令
app.directive('permission', permissionDirective)

// 在应用启动时自动检查认证状态
const authStore = useAuthStore()
authStore.checkAuth()

app.mount('#app')
