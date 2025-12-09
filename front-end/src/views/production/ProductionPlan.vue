<template>
  <div class="container mx-auto p-4">
    <h2 class="text-2xl font-bold mb-4">生产计划管理</h2>
    
    <div class="flex justify-between items-center mb-4">
        <div class="flex space-x-4">
          <el-input v-model="searchQuery" placeholder="搜索工单编号" clearable />
          <el-select v-model="statusFilter" placeholder="状态">
            <el-option label="全部" value="" />
            <el-option label="已计划" value="planned" />
            <el-option label="执行中" value="executing" />
            <el-option label="已完成" value="completed" />
          </el-select>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
        </div>
        <el-button 
          v-if="authStore.hasPermission('create', 'production')"
          type="success" 
          @click="handleAdd"
        >
          新增计划
        </el-button>
      </div>
    
    <el-table :data="productionPlans" style="width: 100%" v-loading="loading">
      <el-table-column prop="id" label="工单编号" width="180" />
      <el-table-column prop="productName" label="产品名称" width="180" />
      <el-table-column prop="plannedQuantity" label="计划数量" width="120" />
      <el-table-column prop="startTime" label="开始时间" width="180" />
      <el-table-column prop="endTime" label="结束时间" width="180" />
      <el-table-column prop="status" label="状态" width="120">
        <template #default="scope">
          <el-tag :type="getStatusType(scope.row.status)">
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="remarks" label="备注" />
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="scope">
          <!-- 查看按钮（所有用户） -->
          <el-button type="primary" size="small" @click="handleView(scope.row)">
            查看
          </el-button>
          
          <!-- 编辑按钮（操作员以上） -->
          <el-button 
            v-if="authStore.hasPermission('edit', 'production')"
            type="warning" 
            size="small" 
            @click="handleEdit(scope.row)"
          >
            编辑
          </el-button>
          
          <!-- 删除按钮（管理员以上） -->
          <el-button 
            v-if="authStore.hasPermission('delete', 'production')"
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
        <el-form-item label="产品名称" required>
          <el-input v-model="formData.productName" placeholder="请输入产品名称" />
        </el-form-item>
        <el-form-item label="计划数量" required>
          <el-input-number v-model="formData.plannedQuantity" :min="1" placeholder="请输入计划数量" />
        </el-form-item>
        <el-form-item label="开始时间" required>
          <el-date-picker
            v-model="formData.startTime"
            type="datetime"
            placeholder="选择开始时间"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="结束时间" required>
          <el-date-picker
            v-model="formData.endTime"
            type="datetime"
            placeholder="选择结束时间"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="formData.status">
            <el-option label="已计划" value="planned" />
            <el-option label="执行中" value="executing" />
            <el-option label="已完成" value="completed" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="formData.remarks" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessageBox, ElMessage, ElLoading } from 'element-plus'
import { useAuthStore } from '../../stores/auth'
// 导入生产计划相关API
import * as productionApi from '../../api/production'

const authStore = useAuthStore()

// 搜索和过滤
const searchQuery = ref('')
const statusFilter = ref('')

// 表格数据
const productionPlans = ref([])

// 分页
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 加载状态
const loading = ref(false)

// 从本地存储加载数据
const loadFromLocalStorage = () => {
  const storedData = localStorage.getItem('productionPlans')
  if (storedData) {
    return JSON.parse(storedData)
  }
  // 默认数据
  return [
    {
      id: 'WO202501001',
      productName: '智能手机外壳',
      plannedQuantity: 1000,
      startTime: '2025-01-01 08:00:00',
      endTime: '2025-01-01 18:00:00',
      status: 'planned',
      remarks: '按订单要求生产'
    },
    {
      id: 'WO202501002',
      productName: '平板电脑屏幕',
      plannedQuantity: 500,
      startTime: '2025-01-02 09:00:00',
      endTime: '2025-01-02 17:00:00',
      status: 'executing',
      remarks: '加急订单'
    },
    {
      id: 'WO202501003',
      productName: '笔记本电脑键盘',
      plannedQuantity: 800,
      startTime: '2025-01-03 08:30:00',
      endTime: '2025-01-03 16:30:00',
      status: 'completed',
      remarks: '常规生产'
    }
  ]
}

// 保存数据到本地存储
const saveToLocalStorage = (data) => {
  localStorage.setItem('productionPlans', JSON.stringify(data))
}

// 加载生产计划数据
const loadProductionPlans = async () => {
  loading.value = true
  try {
    // 先尝试从API获取数据
    const response = await productionApi.getProductionPlansApi()
    // 根据API响应结构调整数据处理
    if (response.data && response.data.code === 0) {
      productionPlans.value = response.data.data || []
      total.value = productionPlans.value.length
      // 保存到本地存储
      saveToLocalStorage(productionPlans.value)
    } else {
      // API返回错误，使用本地存储数据
      productionPlans.value = loadFromLocalStorage()
      total.value = productionPlans.value.length
      ElMessage.warning('使用本地存储数据，API返回格式可能不符合预期')
    }
  } catch (error) {
    console.error('加载生产计划失败:', error)
    // API调用失败，使用本地存储数据
    productionPlans.value = loadFromLocalStorage()
    total.value = productionPlans.value.length
    ElMessage.info('使用本地存储数据')
  } finally {
    loading.value = false
  }
}

// 组件挂载时加载数据
onMounted(() => {
  loadProductionPlans()
})

// 对话框
const dialogVisible = ref(false)
const dialogTitle = ref('新增计划')
const formData = reactive({
  id: '',
  productName: '',
  plannedQuantity: 1,
  startTime: '',
  endTime: '',
  status: 'planned',
  remarks: ''
})

// 状态映射
const statusMap = {
  planned: { text: '已计划', type: 'info' },
  executing: { text: '执行中', type: 'warning' },
  completed: { text: '已完成', type: 'success' }
}

// 获取状态文本
const getStatusText = (status) => {
  return statusMap[status]?.text || status
}

// 获取状态标签类型
const getStatusType = (status) => {
  return statusMap[status]?.type || 'default'
}

// 搜索
const handleSearch = () => {
  // 基于搜索条件过滤本地数据
  const filteredData = loadFromLocalStorage().filter(plan => {
    // 工单编号搜索
    const matchesSearch = !searchQuery.value || plan.id.toLowerCase().includes(searchQuery.value.toLowerCase())
    // 状态过滤
    const matchesStatus = !statusFilter.value || plan.status === statusFilter.value
    return matchesSearch && matchesStatus
  })
  
  productionPlans.value = filteredData
  total.value = filteredData.length
  ElMessage.success('搜索完成，找到 ' + filteredData.length + ' 条记录')
}

// 新增
const handleAdd = () => {
  if (!authStore.hasPermission('create', 'production')) {
    ElMessage.warning('您没有创建权限')
    return
  }
  
  dialogTitle.value = '新增计划'
  Object.assign(formData, {
    id: '',
    productName: '',
    plannedQuantity: 1,
    startTime: '',
    endTime: '',
    status: 'planned',
    remarks: ''
  })
  dialogVisible.value = true
}

// 查看
const handleView = (row) => {
  ElMessage.info(`查看工单: ${row.id}`)
}

// 编辑
const handleEdit = (row) => {
  if (!authStore.hasPermission('edit', 'production')) {
    ElMessage.warning('您没有编辑权限')
    return
  }
  
  dialogTitle.value = '编辑计划'
  Object.assign(formData, { ...row })
  dialogVisible.value = true
}

// 删除
const handleDelete = (row) => {
  if (!authStore.hasPermission('delete', 'production')) {
    ElMessage.warning('您没有删除权限')
    return
  }
  
  ElMessageBox.confirm(`确定要删除工单 ${row.id} 吗？`, '确认删除', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      // 尝试API删除
      try {
        await productionApi.deleteProductionPlanApi(row.id)
      } catch (apiError) {
        console.error('API删除失败，使用本地删除:', apiError)
      }
      
      // 更新本地数据
      const index = productionPlans.value.findIndex(item => item.id === row.id)
      if (index !== -1) {
        productionPlans.value.splice(index, 1)
        total.value = productionPlans.value.length
        // 保存到本地存储
        saveToLocalStorage(productionPlans.value)
        ElMessage.success('删除成功')
      }
    } catch (error) {
      console.error('删除生产计划失败:', error)
      ElMessage.error('删除失败，请稍后重试')
    }
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}

// 生成唯一ID
const generateId = () => {
  const date = new Date().toISOString().slice(0, 10).replace(/-/g, '')
  const random = Math.floor(1000 + Math.random() * 9000)
  return `WO${date}${random}`
}

// 提交
const handleSubmit = async () => {
  if (!formData.productName || !formData.plannedQuantity || !formData.startTime || !formData.endTime) {
    ElMessage.error('请填写完整信息')
    return
  }
  
  try {
    if (formData.id) {
      // 编辑逻辑
      try {
        await productionApi.updateProductionPlanApi(formData.id, formData)
      } catch (apiError) {
        console.error('API更新失败，使用本地更新:', apiError)
      }
      
      // 更新本地数据
      const index = productionPlans.value.findIndex(item => item.id === formData.id)
      if (index !== -1) {
        productionPlans.value[index] = { ...formData }
        // 保存到本地存储
        saveToLocalStorage(productionPlans.value)
        ElMessage.success('编辑成功')
      }
    } else {
      // 新增逻辑
      const newPlan = {
        ...formData,
        id: generateId()
      }
      
      try {
        const response = await productionApi.createProductionPlanApi(newPlan)
        if (response.data && response.data.code === 0 && response.data.data) {
          productionPlans.value.unshift(response.data.data)
        } else {
          // API返回异常，使用本地生成的数据
          productionPlans.value.unshift(newPlan)
        }
      } catch (apiError) {
        console.error('API新增失败，使用本地新增:', apiError)
        // API调用失败，使用本地生成的数据
        productionPlans.value.unshift(newPlan)
      }
      
      total.value = productionPlans.value.length
      // 保存到本地存储
      saveToLocalStorage(productionPlans.value)
      ElMessage.success('新增成功')
    }
    dialogVisible.value = false
  } catch (error) {
    console.error('提交生产计划失败:', error)
    ElMessage.error(formData.id ? '编辑失败' : '新增失败')
  }
}
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
}
</style>