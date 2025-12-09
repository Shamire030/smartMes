<template>
  <div class="container mx-auto p-4">
    <h2 class="text-2xl font-bold mb-4">生产执行管理</h2>
    
    <div class="flex justify-between items-center mb-4">
      <div class="flex space-x-4">
        <el-input v-model="searchQuery" placeholder="搜索工单编号" clearable />
        <el-select v-model="statusFilter" placeholder="状态">
          <el-option label="全部" value="" />
          <el-option label="执行中" value="executing" />
          <el-option label="暂停" value="paused" />
          <el-option label="已完成" value="completed" />
          <el-option label="已终止" value="terminated" />
        </el-select>
        <el-button type="primary" @click="handleSearch">搜索</el-button>
      </div>
    </div>
    
    <el-table :data="filteredExecutions" style="width: 100%">
      <el-table-column prop="id" label="工单编号" width="180" />
      <el-table-column prop="productName" label="产品名称" width="180" />
      <el-table-column prop="plannedQuantity" label="计划数量" width="120" />
      <el-table-column prop="actualQuantity" label="实际数量" width="120" />
      <el-table-column prop="startTime" label="开始时间" width="180" />
      <el-table-column prop="currentTime" label="当前时间" width="180" />
      <el-table-column prop="status" label="状态" width="120">
        <template #default="scope">
          <el-tag :type="getStatusType(scope.row.status)">
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="operator" label="操作员" width="120" />
      <el-table-column label="操作" width="200" fixed="right">
        <template #default="scope">
          <el-button type="primary" size="small" @click="handleDetails(scope.row)">
            详情
          </el-button>
          <el-button 
            v-if="authStore.hasPermission('edit', 'production')"
            :type="scope.row.status === 'executing' ? 'warning' : 'success'" 
            size="small" 
            @click="handleToggleStatus(scope.row)"
          >
            {{ scope.row.status === 'executing' ? '暂停' : '开始' }}
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
    
    <!-- 详情对话框 -->
    <el-dialog
      v-model="detailsVisible"
      title="生产执行详情"
      width="800px"
    >
      <el-descriptions :column="2" border>
        <el-descriptions-item label="工单编号">{{ selectedRow?.id }}</el-descriptions-item>
        <el-descriptions-item label="产品名称">{{ selectedRow?.productName }}</el-descriptions-item>
        <el-descriptions-item label="计划数量">{{ selectedRow?.plannedQuantity }}</el-descriptions-item>
        <el-descriptions-item label="实际数量">{{ selectedRow?.actualQuantity }}</el-descriptions-item>
        <el-descriptions-item label="开始时间">{{ selectedRow?.startTime }}</el-descriptions-item>
        <el-descriptions-item label="当前时间">{{ selectedRow?.currentTime }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ selectedRow?.status ? getStatusText(selectedRow.status) : '' }}</el-descriptions-item>
        <el-descriptions-item label="操作员">{{ selectedRow?.operator }}</el-descriptions-item>
        <el-descriptions-item label="备注" :span="2">{{ selectedRow?.remarks }}</el-descriptions-item>
      </el-descriptions>
      
      <h3 class="mt-4 mb-2 font-bold">生产记录</h3>
      <el-table :data="productionRecords" style="width: 100%">
        <el-table-column prop="time" label="时间" width="180" />
        <el-table-column prop="record" label="记录" />
        <el-table-column prop="operator" label="操作员" width="120" />
      </el-table>
      
      <template #footer>
        <el-button @click="detailsVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '../../stores/auth'

// 权限控制
const authStore = useAuthStore()

// 本地存储相关函数
const loadFromLocalStorage = () => {
  const storedData = localStorage.getItem('productionExecution')
  if (storedData) {
    return JSON.parse(storedData)
  }
  // 默认数据
  return {
    productionExecutions: [
      {
        id: 'WO202501002',
        productName: '平板电脑屏幕',
        plannedQuantity: 500,
        actualQuantity: 250,
        startTime: '2025-01-02 09:00:00',
        currentTime: '2025-01-02 12:30:00',
        status: 'executing',
        operator: '张三',
        remarks: '加急订单'
      },
      {
        id: 'WO202501004',
        productName: '智能手表表带',
        plannedQuantity: 2000,
        actualQuantity: 1000,
        startTime: '2025-01-02 10:00:00',
        currentTime: '2025-01-02 11:30:00',
        status: 'paused',
        operator: '李四',
        remarks: '设备维护'
      },
      {
        id: 'WO202501005',
        productName: '耳机外壳',
        plannedQuantity: 1500,
        actualQuantity: 1500,
        startTime: '2025-01-01 13:00:00',
        currentTime: '2025-01-01 17:00:00',
        status: 'completed',
        operator: '王五',
        remarks: '常规生产'
      }
    ],
    productionRecords: [
      {
        time: '2025-01-02 09:00:00',
        record: '开始生产',
        operator: '张三'
      },
      {
        time: '2025-01-02 10:00:00',
        record: '生产数量达到50',
        operator: '张三'
      },
      {
        time: '2025-01-02 11:00:00',
        record: '生产数量达到150',
        operator: '张三'
      },
      {
        time: '2025-01-02 12:00:00',
        record: '生产数量达到250',
        operator: '张三'
      }
    ]
  }
}

const saveToLocalStorage = (data) => {
  localStorage.setItem('productionExecution', JSON.stringify(data))
}

// 表格数据
const productionExecutions = ref([])

// 生产记录
const productionRecords = ref([])

// 同步数据到本地存储
const syncToLocalStorage = () => {
  saveToLocalStorage({
    productionExecutions: productionExecutions.value,
    productionRecords: productionRecords.value
  })
}

// 搜索和过滤
const searchQuery = ref('')
const statusFilter = ref('')
const filteredExecutions = ref([])

// 分页
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(filteredExecutions.value.length)

// 详情对话框
const detailsVisible = ref(false)
const selectedRow = ref(null)

// 状态映射
const statusMap = {
  executing: { text: '执行中', type: 'warning' },
  paused: { text: '暂停', type: 'info' },
  completed: { text: '已完成', type: 'success' },
  terminated: { text: '已终止', type: 'danger' }
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
  // 过滤生产执行列表
  filteredExecutions.value = productionExecutions.value.filter(execution => {
    const matchesQuery = !searchQuery.value || execution.id.includes(searchQuery.value)
    const matchesStatus = !statusFilter.value || execution.status === statusFilter.value
    return matchesQuery && matchesStatus
  })
  
  // 更新分页信息
  total.value = filteredExecutions.value.length
  currentPage.value = 1
  
  // 如果过滤结果为空，显示提示
  if (filteredExecutions.value.length === 0) {
    ElMessage.info('没有找到匹配的生产执行记录')
  } else {
    ElMessage.success('搜索完成，找到 ' + filteredExecutions.value.length + ' 条记录')
  }
}

// 详情
const handleDetails = (row) => {
  selectedRow.value = row
  detailsVisible.value = true
}

// 切换状态
const handleToggleStatus = (row) => {
  // 检查权限
  if (!authStore.hasPermission('edit', 'production')) {
    ElMessage.warning('您没有权限执行此操作')
    return
  }
  
  // 切换状态
  const newStatus = row.status === 'executing' ? 'paused' : 'executing'
  row.status = newStatus
  
  // 更新当前时间
  row.currentTime = new Date().toISOString().slice(0, 19).replace('T', ' ')
  
  // 同步到本地存储
  syncToLocalStorage()
  
  ElMessage.success('状态已切换为' + getStatusText(newStatus))
}
// 在组件挂载时加载数据
onMounted(() => {
  const data = loadFromLocalStorage()
  productionExecutions.value = data.productionExecutions
  productionRecords.value = data.productionRecords
  // 初始化过滤数据
  filteredExecutions.value = [...productionExecutions.value]
  // 更新总数
  total.value = filteredExecutions.value.length
})
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
}
</style>