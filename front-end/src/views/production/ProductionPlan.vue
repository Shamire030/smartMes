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
      <el-button type="success" @click="handleAdd">新增计划</el-button>
    </div>
    
    <el-table :data="productionPlans" style="width: 100%">
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
          <el-button type="primary" size="small" @click="handleEdit(scope.row)">
            编辑
          </el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope.row)">
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
import { ref, reactive } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'

// 搜索和过滤
const searchQuery = ref('')
const statusFilter = ref('')

// 表格数据
const productionPlans = ref([
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
])

// 分页
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(productionPlans.value.length)

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
  // 这里可以添加实际的搜索逻辑
  console.log('搜索:', searchQuery.value, statusFilter.value)
}

// 新增
const handleAdd = () => {
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

// 编辑
const handleEdit = (row) => {
  dialogTitle.value = '编辑计划'
  Object.assign(formData, { ...row })
  dialogVisible.value = true
}

// 删除
const handleDelete = (row) => {
  elMessageBox.confirm(`确定要删除工单 ${row.id} 吗？`, '确认删除', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 这里可以添加实际的删除逻辑
    elMessage.success('删除成功')
  }).catch(() => {
    elMessage.info('已取消删除')
  })
}

// 提交
const handleSubmit = () => {
  // 这里可以添加实际的提交逻辑
  dialogVisible.value = false
  elMessage.success(dialogTitle.value === '新增计划' ? '新增成功' : '编辑成功')
}
</script>

<style scoped>
.container {
  max-width: 1200px;
  margin: 0 auto;
}
</style>