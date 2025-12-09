import { useAuthStore } from '../stores/auth'

export const permissionDirective = {
  mounted(el, binding) {
    const authStore = useAuthStore()
    const { value } = binding
    
    if (value && value.permission) {
      const hasPerm = authStore.hasPermission(value.permission, value.page)
      if (!hasPerm) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    }
  }
}