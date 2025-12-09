<template>
  <div class="container mx-auto p-4">
    <h2 class="text-2xl font-bold mb-4">设备管理</h2>
    
    <el-tabs v-model="activeTab">
      <el-tab-pane label="设备列表" name="list">
        <div class="flex justify-between items-center mb-4">
          <div class="flex space-x-4">
            <el-input v-model="searchQuery" placeholder="搜索设备编号/名称" clearable />
            <el-select v-model="statusFilter" placeholder="状态">
              <el-option label="全部" value="" />
              <el-option label="运行中" value="running" />
              <el-option label="待机" value="idle" />
              <el-option label="维护中" value="maintenance" />
              <el-option label="故障" value="fault" />
            </el-select>
            <el-button type="primary" @click="handleSearch">搜索</el-button>
          </div>
          <el-button 
  v-if="authStore.hasPermission('create', 'equipment')"
  type="success" 
  @click="handleAdd"
>新增设备</el-button>
        </div>
        
        <el-table :data="equipmentList" style="width: 100%">
          <el-table-column prop="id" label="设备编号" width="180" />
          <el-table-column prop="name" label="设备名称" width="180" />
          <el-table-column prop="type" label="设备类型" width="120" />
          <el-table-column prop="status" label="状态" width="120">
            <template #default="scope">
              <el-tag :type="getStatusType(scope.row.status)">
                {{ getStatusText(scope.row.status) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="location" label="设备位置" width="180" />
          <el-table-column prop="manufacturer" label="制造商" width="180" />
          <el-table-column prop="purchaseDate" label="购买日期" width="180" />
          <el-table-column prop="lastMaintenanceDate" label="上次维护日期" width="180" />
          <el-table-column label="操作" width="200" fixed="right">
            <template #default="scope">
              <el-button type="primary" size="small" @click="handleDetails(scope.row)">
                详情
              </el-button>
              <el-button type="warning" size="small" @click="handleMaintenance(scope.row)">
                维护记录
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      
      <el-tab-pane label="设备状态监控" name="monitoring">
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          <el-card v-for="equipment in equipmentList" :key="equipment.id" class="monitor-card">
            <template #header>
              <div class="flex justify-between items-center">
                <span>{{ equipment.name }}</span>
                <el-tag :type="getStatusType(equipment.status)">
                  {{ getStatusText(equipment.status) }}
                </el-tag>
              </div>
            </template>
            <div class="text-center">
              <div class="text-3xl font-bold mb-2">{{ equipment.currentTemperature }}°C</div>
              <div class="text-gray-600 mb-4">当前温度</div>
              <div class="flex justify-between">
                <div>
                  <div class="text-sm text-gray-500">运行时间</div>
                  <div class="font-medium">{{ equipment.runningHours }}小时</div>
                </div>
                <div>
                  <div class="text-sm text-gray-500">产量</div>
                  <div class="font-medium">{{ equipment.productionCount }}件</div>
                </div>
              </div>
            </div>
          </el-card>
        </div>
      </el-tab-pane>
    </el-tabs>
    
    <!-- 详情对话框 -->
    <el-dialog
      v-model="detailsVisible"
      title="设备详情"
      width="800px"
    >
      <el-descriptions :column="2" border>
        <el-descriptions-item label="设备编号">{{ selectedRow?.id }}</el-descriptions-item>
        <el-descriptions-item label="设备名称">{{ selectedRow?.name }}</el-descriptions-item>
        <el-descriptions-item label="设备类型">{{ selectedRow?.type }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ selectedRow?.status ? getStatusText(selectedRow.status) : '' }}</el-descriptions-item>
        <el-descriptions-item label="设备位置">{{ selectedRow?.location }}</el-descriptions-item>
        <el-descriptions-item label="制造商">{{ selectedRow?.manufacturer }}</el-descriptions-item>
        <el-descriptions-item label="购买日期">{{ selectedRow?.purchaseDate }}</el-descriptions-item>
        <el-descriptions-item label="上次维护日期">{{ selectedRow?.lastMaintenanceDate }}</el-descriptions-item>
        <el-descriptions-item label="当前温度">{{ selectedRow?.currentTemperature }}°C</el-descriptions-item>
        <el-descriptions-item label="运行时间">{{ selectedRow?.runningHours }}小时</el-descriptions-item>
        <el-descriptions-item label="备注" :span="2">{{ selectedRow?.remarks }}</el-descriptions-item>
      </el-descriptions>
      
      <template #footer>
        <el-button @click="detailsVisible = false">关闭</el-button>
      </template>
    </el-dialog>
    
    <!-- 维护记录对话框 -->
    <el-dialog
      v-model="maintenanceVisible"
      :title="maintenanceTitle"
      width="600px"
    >
      <el-table :data="maintenanceRecords.filter(r => r.equipmentId === selectedEquipment?.id)" style="width: 100%">
        <el-table-column prop="id" label="记录编号" width="150" />
        <el-table-column prop="date" label="维护日期" width="120" />
        <el-table-column prop="type" label="维护类型" width="100" />
        <el-table-column prop="content" label="维护内容" />
        <el-table-column prop="technician" label="维护人员" width="100" />
        <el-table-column prop="duration" label="维护时长(小时)" width="120" />
      </el-table>
      
      <template #footer>
        <el-button @click="maintenanceVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useAuthStore } from '../../stores/auth'

// 标签页
const activeTab = ref('list')

// 权限控制
const authStore = useAuthStore()

// 设备列表
const searchQuery = ref('')
const statusFilter = ref('')
const equipmentList = ref([])

// 维护记录对话框
const maintenanceVisible = ref(false)
const maintenanceTitle = ref('设备维护记录')
const selectedEquipment = ref(null)
const maintenanceRecords = ref([])

// 本地存储相关函数
const loadFromLocalStorage = () => {
  const storedData = localStorage.getItem('equipmentManagement')
  if (storedData) {
    return JSON.parse(storedData)
  }
  // 默认数据
  return {
    equipmentList: [
      {
        id: 'EQ202501001',
        name: '注塑机',
        type: '成型设备',
        status: 'running',
        location: '车间A-101',
        manufacturer: '海天',
        purchaseDate: '2023-01-15',
        lastMaintenanceDate: '2024-12-10',
        currentTemperature: 65,
        runningHours: 4500,
        productionCount: 25000,
        remarks: '主要生产手机外壳'
      },
      {
        id: 'EQ202501002',
        name: 'CNC加工中心',
        type: '加工设备',
        status: 'idle',
        location: '车间B-201',
        manufacturer: '台群',
        purchaseDate: '2023-03-20',
        lastMaintenanceDate: '2024-11-20',
        currentTemperature: 45,
        runningHours: 3800,
        productionCount: 18000,
        remarks: '加工高精度零件'
      },
      {
        id: 'EQ202501003',
        name: '包装机',
        type: '包装设备',
        status: 'maintenance',
        location: '车间C-301',
        manufacturer: '利乐',
        purchaseDate: '2023-05-10',
        lastMaintenanceDate: '2024-12-15',
        currentTemperature: 35,
        runningHours: 3200,
        productionCount: 32000,
        remarks: '包装成品'
      },
      {
        id: 'EQ202501004',
        name: '检测设备',
        type: '检测设备',
        status: 'fault',
        location: '质检室D-401',
        manufacturer: '蔡司',
        purchaseDate: '2023-08-05',
        lastMaintenanceDate: '2024-10-30',
        currentTemperature: 30,
        runningHours: 2800,
        productionCount: 15000,
        remarks: '产品质量检测'
      }
    ],
    maintenanceRecords: [
      {
        id: 'MT202501001',
        equipmentId: 'EQ202501001',
        date: '2024-12-10',
        type: '日常维护',
        content: '更换润滑油',
        technician: '张三',
        duration: 2
      },
      {
        id: 'MT202501002',
        equipmentId: 'EQ202501003',
        date: '2024-12-15',
        type: '故障维修',
        content: '修复传动带故障',
        technician: '李四',
        duration: 4
      }
    ]
  }
}

const saveToLocalStorage = (data) => {
  localStorage.setItem('equipmentManagement', JSON.stringify(data))
}

// 同步数据到本地存储
const syncToLocalStorage = () => {
  saveToLocalStorage({
    equipmentList: equipmentList.value,
    maintenanceRecords: maintenanceRecords.value
  })
}

// 详情对话框
const detailsVisible = ref(false)
const selectedRow = ref(null)

// 状态映射
const statusMap = {
  running: { text: '运行中', type: 'success' },
  idle: { text: '待机', type: 'info' },
  maintenance: { text: '维护中', type: 'warning' },
  fault: { text: '故障', type: 'danger' }
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
  // 获取原始数据
  const allEquipment = loadFromLocalStorage().equipmentList
  // 过滤设备列表
  const filtered = allEquipment.filter(equipment => {
    const matchesQuery = !searchQuery.value || 
      equipment.id.includes(searchQuery.value) || 
      equipment.name.includes(searchQuery.value)
    const matchesStatus = !statusFilter.value || equipment.status === statusFilter.value
    return matchesQuery && matchesStatus
  })
  
  // 更新设备列表
  equipmentList.value = filtered
  
  // 如果过滤结果为空，显示提示
  if (filtered.length === 0) {
    ElMessage.info('没有找到匹配的设备')
  } else {
    ElMessage.success('搜索完成，找到 ' + filtered.length + ' 条记录')
  }
}

// 新增
const handleAdd = () => {
  // 创建新设备
  const newEquipment = {
    id: 'EQ' + new Date().toISOString().slice(0, 10).replace(/-/g, '') + Math.floor(Math.random() * 1000).toString().padStart(3, '0'),
    name: '新设备',
    type: '通用设备',
    status: 'idle',
    location: '未分配',
    manufacturer: '未知',
    purchaseDate: new Date().toISOString().slice(0, 10),
    lastMaintenanceDate: '',
    currentTemperature: 0,
    runningHours: 0,
    productionCount: 0,
    remarks: '新添加的设备'
  }
  
  // 添加到列表
  equipmentList.value.unshift(newEquipment)
  
  // 同步到本地存储
  syncToLocalStorage()
  
  ElMessage.success('设备新增成功')
}

// 详情
const handleDetails = (row) => {
  selectedRow.value = row
  detailsVisible.value = true
}

// 维护记录
const handleMaintenance = (row) => {
  selectedEquipment.value = row
  maintenanceTitle.value = row.name + ' - 维护记录'
  
  // 过滤该设备的维护记录
  const filteredRecords = maintenanceRecords.value.filter(record => record.equipmentId === row.id)
  
  if (filteredRecords.length === 0) {
    ElMessage.info('该设备暂无维护记录')
  }
  
  // 无论是否有记录，都显示对话框
  maintenanceVisible.value = true
}
// 在组件挂载时加载数据
onMounted(() => {
  const data = loadFromLocalStorage()
  equipmentList.value = data.equipmentList
  maintenanceRecords.value = data.maintenanceRecords
})
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
}

.monitor-card {
  height: 200px;
}
</style>