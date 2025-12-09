import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/auth'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue'),
    meta: { requiresAuth: false }
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('../views/Layout.vue'),
    meta: { requiresAuth: true },
    children: [
      {
        path: '',
        name: 'Dashboard',
        component: () => import('../views/Dashboard.vue')
      },
      {
        path: 'production-plan',
        name: 'ProductionPlan',
        component: () => import('../views/production/ProductionPlan.vue')
      },
      {
        path: 'production-execution',
        name: 'ProductionExecution',
        component: () => import('../views/production/ProductionExecution.vue')
      },
      {
        path: 'quality-management',
        name: 'QualityManagement',
        component: () => import('../views/quality/QualityManagement.vue')
      },
      {
        path: 'equipment-management',
        name: 'EquipmentManagement',
        component: () => import('../views/equipment/EquipmentManagement.vue')
      },
      {
        path: 'user-management',
        name: 'UserManagement',
        component: () => import('../views/system/UserManagement.vue')
      },
      {
        path: 'role-management',
        name: 'RoleManagement',
        component: () => import('../views/system/RoleManagement.vue')
      }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('../views/NotFound.vue')
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth)
  
  if (requiresAuth && !authStore.isAuthenticated) {
    next({ name: 'Login' })
  } else if (!requiresAuth && authStore.isAuthenticated && to.name === 'Login') {
    next({ name: 'Dashboard' })
  } else {
    next()
  }
})

export default router