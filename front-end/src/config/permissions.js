export const PERMISSION_CONFIG = {
  // 角色定义
  roles: {
    read_only: {
      name: '只读用户',
      level: 1,
      permissions: ['view', 'read']
    },
    operator: {
      name: '操作员', 
      level: 2,
      permissions: ['view', 'read', 'create', 'edit_own']
    },
    admin: {
      name: '管理员',
      level: 3,
      permissions: ['view', 'read', 'create', 'edit', 'delete', 'export']
    },
    super_admin: {
      name: '超级管理员',
      level: 99,
      permissions: ['*']  // 所有权限
    }
  },

  // 页面权限映射
  pagePermissions: {
    // 仪表盘
    dashboard: {
      view: ['read_only', 'operator', 'admin', 'super_admin'],
      export: ['admin', 'super_admin']
    },
    
    // 生产管理
    production: {
      view: ['read_only', 'operator', 'admin', 'super_admin'],
      create: ['operator', 'admin', 'super_admin'],
      edit: ['operator', 'admin', 'super_admin'],
      delete: ['admin', 'super_admin'],
      export: ['admin', 'super_admin']
    },
    
    // 质量管理
    quality: {
      view: ['read_only', 'operator', 'admin', 'super_admin'],
      create: ['operator', 'admin', 'super_admin'],
      edit: ['admin', 'super_admin'],
      delete: ['super_admin'],
      approve: ['admin', 'super_admin']
    },
    
    // 设备管理
    equipment: {
      view: ['read_only', 'operator', 'admin', 'super_admin'],
      control: ['operator', 'admin', 'super_admin'],
      maintain: ['admin', 'super_admin'],
      config: ['super_admin']
    },
    
    // 系统管理
    system: {
      view: ['admin', 'super_admin'],
      user_manage: ['super_admin'],
      role_manage: ['super_admin'],
      system_config: ['super_admin']
    }
  }
}

// 权限检查工具
export const hasPermission = (userRole, permission, page = '') => {
  if (userRole === 'super_admin') return true
  
  const roleConfig = PERMISSION_CONFIG.roles[userRole]
  if (!roleConfig) return false
  
  // 检查通配符权限
  if (roleConfig.permissions.includes('*')) return true
  
  // 如果没有指定页面，只检查角色是否有该权限
  if (!page) {
    return roleConfig.permissions.includes(permission)
  }
  
  // 获取页面权限配置
  const pagePerms = PERMISSION_CONFIG.pagePermissions[page]
  if (pagePerms && pagePerms[permission]) {
    // 检查用户角色是否在该页面的该权限列表中
    return pagePerms[permission].includes(userRole)
  }
  
  // 如果没有页面级别的权限配置，回退到角色权限检查
  return roleConfig.permissions.includes(permission)
}

// 页面权限检查
export const canAccessPage = (userRole, page) => {
  const pagePerms = PERMISSION_CONFIG.pagePermissions[page]
  if (!pagePerms) return false
  
  return pagePerms.view.includes(userRole)
}