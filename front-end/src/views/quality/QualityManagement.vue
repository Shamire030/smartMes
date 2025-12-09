<template>
  <div class="container mx-auto p-4">
    <h2 class="text-2xl font-bold mb-4">质量管理</h2>
    
    <el-tabs v-model="activeTab">
      <el-tab-pane label="质量检查记录" name="inspection">
        <div class="flex justify-between items-center mb-4">
          <div class="flex space-x-4">
            <el-input v-model="inspectionQuery" placeholder="搜索工单编号" clearable />
            <el-select v-model="inspectionStatusFilter" placeholder="状态">
              <el-option label="全部" value="" />
              <el-option label="合格" value="pass" />
              <el-option label="不合格" value="fail" />
            </el-select>
            <el-button type="primary" @click="handleInspectionSearch">搜索</el-button>
          </div>
          <el-button 
        v-if="authStore.hasPermission('create', 'quality')"
        type="success" 
        @click="handleAddInspection"
      >新增检查记录</el-button>
        </div>
        
        <el-table :data="inspectionRecords" style="width: 100%">
          <el-table-column prop="id" label="检查编号" width="180" />
          <el-table-column prop="workOrderId" label="工单编号" width="180" />
          <el-table-column prop="productName" label="产品名称" width="180" />
          <el-table-column prop="inspectionDate" label="检查日期" width="180" />
          <el-table-column prop="inspector" label="检验员" width="120" />
          <el-table-column prop="status" label="状态" width="120">
            <template #default="scope">
              <el-tag :type="scope.row.status === 'pass' ? 'success' : 'danger'">
                {{ scope.row.status === 'pass' ? '合格' : '不合格' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="defectCount" label="缺陷数量" width="120" />
          <el-table-column label="操作" width="200" fixed="right">
            <template #default="scope">
              <el-button type="primary" size="small" @click="handleInspectionDetails(scope.row)">
                详情
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      
      <el-tab-pane label="不合格品管理" name="nonconforming">
        <div class="flex justify-between items-center mb-4">
          <div class="flex space-x-4">
            <el-input v-model="nonconformingQuery" placeholder="搜索不合格品编号" clearable />
            <el-select v-model="dispositionFilter" placeholder="处理方式">
              <el-option label="全部" value="" />
              <el-option label="返工" value="rework" />
              <el-option label="报废" value="scrap" />
              <el-option label="让步接收" value="accept" />
            </el-select>
            <el-button type="primary" @click="handleNonconformingSearch">搜索</el-button>
          </div>
        </div>
        
        <el-table :data="nonconformingItems" style="width: 100%">
          <el-table-column prop="id" label="编号" width="180" />
          <el-table-column prop="workOrderId" label="工单编号" width="180" />
          <el-table-column prop="productName" label="产品名称" width="180" />
          <el-table-column prop="defectType" label="缺陷类型" width="120" />
          <el-table-column prop="quantity" label="数量" width="80" />
          <el-table-column prop="disposition" label="处理方式" width="120">
            <template #default="scope">
              <el-tag :type="getDispositionType(scope.row.disposition)">
                {{ getDispositionText(scope.row.disposition) }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="dispositionDate" label="处理日期" width="180" />
          <el-table-column prop="disposedBy" label="处理人" width="120" />
          <el-table-column label="操作" width="200" fixed="right">
            <template #default="scope">
              <el-button type="primary" size="small" @click="handleNonconformingDetails(scope.row)">
                详情
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
    
    <!-- 详情对话框 -->
    <el-dialog
      v-model="detailsVisible"
      :title="detailsTitle"
      width="600px"
    >
      <el-descriptions :column="2" border>
        <el-descriptions-item v-if="activeTab === 'inspection'" label="检查编号">{{ selectedRow?.id }}</el-descriptions-item>
        <el-descriptions-item v-else label="不合格品编号">{{ selectedRow?.id }}</el-descriptions-item>
        <el-descriptions-item label="工单编号">{{ selectedRow?.workOrderId }}</el-descriptions-item>
        <el-descriptions-item label="产品名称">{{ selectedRow?.productName }}</el-descriptions-item>
        <el-descriptions-item v-if="activeTab === 'inspection'" label="检查日期">{{ selectedRow?.inspectionDate }}</el-descriptions-item>
        <el-descriptions-item v-else label="缺陷类型">{{ selectedRow?.defectType }}</el-descriptions-item>
        <el-descriptions-item v-if="activeTab === 'inspection'" label="检验员">{{ selectedRow?.inspector }}</el-descriptions-item>
        <el-descriptions-item v-else label="数量">{{ selectedRow?.quantity }}</el-descriptions-item>
        <el-descriptions-item v-if="activeTab === 'inspection'" label="状态">{{ selectedRow?.status === 'pass' ? '合格' : '不合格' }}</el-descriptions-item>
        <el-descriptions-item v-else label="处理方式">{{ selectedRow?.disposition ? getDispositionText(selectedRow.disposition) : '' }}</el-descriptions-item>
        <el-descriptions-item v-if="activeTab === 'inspection'" label="缺陷数量">{{ selectedRow?.defectCount }}</el-descriptions-item>
        <el-descriptions-item v-else label="处理日期">{{ selectedRow?.dispositionDate }}</el-descriptions-item>
        <el-descriptions-item v-if="activeTab === 'inspection'" label="备注" :span="2">{{ selectedRow?.remarks }}</el-descriptions-item>
        <el-descriptions-item v-else label="处理人" :span="2">{{ selectedRow?.disposedBy }}</el-descriptions-item>
      </el-descriptions>
      
      <template #footer>
        <el-button @click="detailsVisible = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useAuthStore } from '../../stores/auth'

// 标签页
const activeTab = ref('inspection')

// 权限控制
const authStore = useAuthStore()

// 检查记录
const inspectionQuery = ref('')
const inspectionStatusFilter = ref('')
const inspectionRecords = ref([])

// 本地存储相关函数
const loadFromLocalStorage = () => {
  const storedData = localStorage.getItem('qualityManagement')
  if (storedData) {
    return JSON.parse(storedData)
  }
  // 默认数据
  return {
    inspectionRecords: [
      {
        id: 'QC202501001',
        workOrderId: 'WO202501002',
        productName: '平板电脑屏幕',
        inspectionDate: '2025-01-02 10:00:00',
        inspector: '李四',
        status: 'pass',
        defectCount: 0,
        remarks: '全部合格'
      },
      {
        id: 'QC202501002',
        workOrderId: 'WO202501003',
        productName: '笔记本电脑键盘',
        inspectionDate: '2025-01-03 15:00:00',
        inspector: '王五',
        status: 'fail',
        defectCount: 2,
        remarks: '发现2个键盘按键不灵敏'
      }
    ],
    nonconformingItems: [
      {
        id: 'NC202501001',
        workOrderId: 'WO202501003',
        productName: '笔记本电脑键盘',
        defectType: '按键不灵敏',
        quantity: 2,
        disposition: 'rework',
        dispositionDate: '2025-01-03 16:00:00',
        disposedBy: '王五'
      },
      {
        id: 'NC202501002',
        workOrderId: 'WO202501001',
        productName: '智能手机外壳',
        defectType: '表面刮伤',
        quantity: 5,
        disposition: 'scrap',
        dispositionDate: '2025-01-01 17:00:00',
        disposedBy: '张三'
      }
    ]
  }
}

const saveToLocalStorage = (data) => {
  localStorage.setItem('qualityManagement', JSON.stringify(data))
}

// 同步数据到本地存储
const syncToLocalStorage = () => {
  saveToLocalStorage({
    inspectionRecords: inspectionRecords.value,
    nonconformingItems: nonconformingItems.value
  })
}

// 不合格品
const nonconformingQuery = ref('')
const dispositionFilter = ref('')
const nonconformingItems = ref([])

// 详情对话框
const detailsVisible = ref(false)
const detailsTitle = ref('详情')
const selectedRow = ref(null)

// 处理方式映射
const dispositionMap = {
  rework: { text: '返工', type: 'warning' },
  scrap: { text: '报废', type: 'danger' },
  accept: { text: '让步接收', type: 'info' }
}

// 获取处理方式文本
const getDispositionText = (disposition) => {
  return dispositionMap[disposition]?.text || disposition
}

// 获取处理方式类型
const getDispositionType = (disposition) => {
  return dispositionMap[disposition]?.type || 'default'
}

// 检查记录搜索
const handleInspectionSearch = () => {
  // 获取原始数据
  const allInspections = loadFromLocalStorage().inspectionRecords
  // 过滤数据
  const filtered = allInspections.filter(record => {
    const matchesQuery = !inspectionQuery.value || record.workOrderId.includes(inspectionQuery.value)
    const matchesStatus = !inspectionStatusFilter.value || record.status === inspectionStatusFilter.value
    return matchesQuery && matchesStatus
  })
  inspectionRecords.value = filtered
  ElMessage.success('搜索完成，找到 ' + filtered.length + ' 条记录')
}

// 新增检查记录
const handleAddInspection = () => {
  if (!authStore.hasPermission('create', 'quality')) {
    ElMessage.warning('您没有创建权限')
    return
  }
  
  // 实现实际的新增逻辑
  const newRecord = {
    id: 'QC' + new Date().toISOString().slice(0, 10).replace(/-/g, '') + Math.floor(Math.random() * 1000).toString().padStart(3, '0'),
    workOrderId: 'WO' + new Date().toISOString().slice(0, 10).replace(/-/g, '') + Math.floor(Math.random() * 1000).toString().padStart(3, '0'),
    productName: '新产品',
    inspectionDate: new Date().toISOString().slice(0, 19).replace('T', ' '),
    inspector: '当前用户',
    status: 'pass',
    defectCount: 0,
    remarks: '新添加的检查记录'
  }
  
  inspectionRecords.value.unshift(newRecord)
  // 同步到本地存储
  syncToLocalStorage()
  ElMessage.success('检查记录新增成功')
}

// 检查记录详情
const handleInspectionDetails = (row) => {
  detailsTitle.value = '质量检查详情'
  selectedRow.value = row
  detailsVisible.value = true
}

// 不合格品搜索
const handleNonconformingSearch = () => {
  // 获取原始数据
  const allNonconforming = loadFromLocalStorage().nonconformingItems
  // 过滤数据
  const filtered = allNonconforming.filter(item => {
    const matchesQuery = !nonconformingQuery.value || item.id.includes(nonconformingQuery.value)
    const matchesDisposition = !dispositionFilter.value || item.disposition === dispositionFilter.value
    return matchesQuery && matchesDisposition
  })
  nonconformingItems.value = filtered
  ElMessage.success('搜索完成，找到 ' + filtered.length + ' 条记录')
}

// 不合格品详情
const handleNonconformingDetails = (row) => {
  detailsTitle.value = '不合格品详情'
  selectedRow.value = row
  detailsVisible.value = true
}

// 在组件挂载时加载数据
onMounted(() => {
  const data = loadFromLocalStorage()
  inspectionRecords.value = data.inspectionRecords
  nonconformingItems.value = data.nonconformingItems
})
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
}
</style>