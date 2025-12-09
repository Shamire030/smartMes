<template>
  <div class="dashboard-container">
    <el-card shadow="never" class="page-header">
      <div class="page-title">
        <h1><i class="el-icon-s-data"></i> 生产总览</h1>
        <p>实时监控生产状况，掌握关键指标</p>
      </div>
    </el-card>
    
    <!-- 统计卡片 -->
    <div class="stats-cards">
      <el-card class="stat-card" shadow="hover">
        <div class="stat-content">
          <div class="stat-info">
            <p class="stat-label">今日生产工单</p>
            <h3 class="stat-value">{{ stats.todayOrders }}</h3>
            <p class="stat-change">
              <span :class="{ 'stat-increase': stats.ordersChange > 0, 'stat-decrease': stats.ordersChange < 0 }">
                {{ stats.ordersChange > 0 ? '+' : '' }}{{ stats.ordersChange }}%
              </span>
              较昨日
            </p>
          </div>
          <div class="stat-icon orders-icon">
            <i class="el-icon-document"></i>
          </div>
        </div>
      </el-card>
      
      <el-card class="stat-card" shadow="hover">
        <div class="stat-content">
          <div class="stat-info">
            <p class="stat-label">生产完成率</p>
            <h3 class="stat-value">{{ stats.completionRate }}%</h3>
            <p class="stat-change">
              <span :class="{ 'stat-increase': stats.completionChange > 0, 'stat-decrease': stats.completionChange < 0 }">
                {{ stats.completionChange > 0 ? '+' : '' }}{{ stats.completionChange }}%
              </span>
              较昨日
            </p>
          </div>
          <div class="stat-icon completion-icon">
            <i class="el-icon-check"></i>
          </div>
        </div>
      </el-card>
      
      <el-card class="stat-card" shadow="hover">
        <div class="stat-content">
          <div class="stat-info">
            <p class="stat-label">产品合格率</p>
            <h3 class="stat-value">{{ stats.qualifiedRate }}%</h3>
            <p class="stat-change">
              <span :class="{ 'stat-increase': stats.qualifiedChange > 0, 'stat-decrease': stats.qualifiedChange < 0 }">
                {{ stats.qualifiedChange > 0 ? '+' : '' }}{{ stats.qualifiedChange }}%
              </span>
              较昨日
            </p>
          </div>
          <div class="stat-icon qualified-icon">
            <i class="el-icon-success"></i>
          </div>
        </div>
      </el-card>
      
      <el-card class="stat-card" shadow="hover">
        <div class="stat-content">
          <div class="stat-info">
            <p class="stat-label">设备OEE</p>
            <h3 class="stat-value">{{ stats.oee }}%</h3>
            <p class="stat-change">
              <span :class="{ 'stat-increase': stats.oeeChange > 0, 'stat-decrease': stats.oeeChange < 0 }">
                {{ stats.oeeChange > 0 ? '+' : '' }}{{ stats.oeeChange }}%
              </span>
              较昨日
            </p>
          </div>
          <div class="stat-icon oee-icon">
            <i class="el-icon-s-tools"></i>
          </div>
        </div>
      </el-card>
    </div>
    
    <!-- 图表区域 -->
    <div class="charts-container">
      <el-card shadow="hover" class="chart-card">
        <template #header>
          <div class="card-header">
            <span>生产趋势图</span>
            <el-select v-model="chartPeriod" placeholder="选择时间范围" size="small">
              <el-option label="今日" value="today"></el-option>
              <el-option label="本周" value="week"></el-option>
              <el-option label="本月" value="month"></el-option>
              <el-option label="本年" value="year"></el-option>
            </el-select>
          </div>
        </template>
        <div ref="productionChartRef" class="chart"></div>
      </el-card>
      
      <el-card shadow="hover" class="chart-card">
        <template #header>
          <div class="card-header">
            <span>设备状态分布</span>
          </div>
        </template>
        <div ref="equipmentChartRef" class="chart"></div>
      </el-card>
    </div>
    
    <!-- 生产工单列表 -->
    <el-card shadow="hover" class="table-card">
      <template #header>
          <div class="card-header">
            <span>最新生产工单</span>
            <div class="header-buttons">
              <!-- 只有管理员以上才能看到导出按钮 -->
              <el-button 
                v-permission="{ permission: 'export', page: 'dashboard' }"
                type="primary" 
                size="small" 
                @click="handleExport"
              >
                <i class="el-icon-download"></i> 导出数据
              </el-button>
              
              <!-- 只有操作员以上才能看到新建按钮 -->
              <el-button 
                v-permission="{ permission: 'create', page: 'dashboard' }"
                type="success" 
                size="small" 
                @click="handleCreateOrder"
              >
                <i class="el-icon-plus"></i> 新建工单
              </el-button>
            </div>
          </div>
        </template>
      
      <el-table :data="orders" style="width: 100%">
        <el-table-column prop="orderNo" label="工单编号" width="180"></el-table-column>
        <el-table-column prop="productName" label="产品名称" width="200"></el-table-column>
        <el-table-column prop="planQuantity" label="计划数量" width="100"></el-table-column>
        <el-table-column prop="completedQuantity" label="完成数量" width="100"></el-table-column>
        <el-table-column prop="progress" label="进度" width="150">
          <template #default="scope">
            <el-progress :percentage="scope.row.progress" :color="getProgressColor(scope.row.progress)"></el-progress>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="120">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">{{ scope.row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="scope">
            <!-- 查看按钮（所有用户） -->
            <el-button type="primary" size="small" @click="handleViewOrder(scope.row)">
              查看
            </el-button>
            
            <!-- 编辑按钮（操作员以上） -->
            <el-button 
              v-permission="{ permission: 'edit', page: 'production' }"
              type="warning" 
              size="small" 
              @click="handleEditOrder(scope.row)"
            >
              编辑
            </el-button>
            
            <!-- 删除按钮（管理员以上） -->
            <el-button 
              v-permission="{ permission: 'delete', page: 'production' }"
              type="danger" 
              size="small" 
              @click="handleDeleteOrder(scope.row)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, onBeforeUnmount } from 'vue'
import * as echarts from 'echarts'
import { ElMessage } from 'element-plus'

// 统计数据
const stats = ref({
  todayOrders: 128,
  ordersChange: 5.2,
  completionRate: 85.6,
  completionChange: 2.3,
  qualifiedRate: 98.2,
  qualifiedChange: 0.8,
  oee: 78.5,
  oeeChange: -1.2
})

// 图表容器
const productionChartRef = ref(null)
const equipmentChartRef = ref(null)
const productionChart = ref(null)
const equipmentChart = ref(null)

// 图表周期
const chartPeriod = ref('today')

// 生产工单数据
const orders = ref([
  {
    orderNo: 'PO20250320001',
    productName: '智能手机外壳',
    planQuantity: 5000,
    completedQuantity: 4200,
    progress: 84,
    status: '生产中',
    createTime: '2025-03-20 08:30:00'
  },
  {
    orderNo: 'PO20250320002',
    productName: '平板电脑屏幕',
    planQuantity: 3000,
    completedQuantity: 3000,
    progress: 100,
    status: '已完成',
    createTime: '2025-03-20 09:15:00'
  },
  {
    orderNo: 'PO20250320003',
    productName: '智能手表表带',
    planQuantity: 8000,
    completedQuantity: 2500,
    progress: 31,
    status: '生产中',
    createTime: '2025-03-20 10:00:00'
  },
  {
    orderNo: 'PO20250320004',
    productName: '耳机充电盒',
    planQuantity: 4000,
    completedQuantity: 0,
    progress: 0,
    status: '待生产',
    createTime: '2025-03-20 10:45:00'
  },
  {
    orderNo: 'PO20250320005',
    productName: '笔记本电脑键盘',
    planQuantity: 2500,
    completedQuantity: 1800,
    progress: 72,
    status: '生产中',
    createTime: '2025-03-20 11:30:00'
  }
])

// 初始化生产趋势图
const initProductionChart = () => {
  if (productionChart.value) {
    productionChart.value.dispose()
  }
  
  productionChart.value = echarts.init(productionChartRef.value)
  
  const option = {
    tooltip: {
      trigger: 'axis',
      formatter: '{b}<br/>{a0}: {c0}件',
      axisPointer: {
        type: 'cross'
      }
    },
    legend: {
      data: ['生产数量']
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: ['00:00', '02:00', '04:00', '06:00', '08:00', '10:00', '12:00', '14:00', '16:00', '18:00', '20:00', '22:00']
    },
    yAxis: {
      type: 'value',
      name: '生产数量（件）'
    },
    series: [
      {
        name: '生产数量',
        type: 'line',
        smooth: true,
        data: [120, 132, 101, 134, 90, 230, 210, 250, 280, 270, 290, 300],
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(59, 130, 246, 0.3)' },
            { offset: 1, color: 'rgba(59, 130, 246, 0.1)' }
          ])
        },
        lineStyle: {
          color: '#3b82f6'
        },
        itemStyle: {
          color: '#3b82f6'
        }
      }
    ]
  }
  
  productionChart.value.setOption(option)
}

// 初始化设备状态分布图表
const initEquipmentChart = () => {
  if (equipmentChart.value) {
    equipmentChart.value.dispose()
  }
  
  equipmentChart.value = echarts.init(equipmentChartRef.value)
  
  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c}台 ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      data: ['运行中', '停机', '待机', '维护中', '故障']
    },
    series: [
      {
        name: '设备状态',
        type: 'pie',
        radius: '65%',
        center: ['50%', '50%'],
        data: [
          { value: 35, name: '运行中', itemStyle: { color: '#67c23a' } },
          { value: 12, name: '停机', itemStyle: { color: '#909399' } },
          { value: 8, name: '待机', itemStyle: { color: '#e6a23c' } },
          { value: 5, name: '维护中', itemStyle: { color: '#409eff' } },
          { value: 3, name: '故障', itemStyle: { color: '#f56c6c' } }
        ],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  }
  
  equipmentChart.value.setOption(option)
}

// 获取进度颜色
const getProgressColor = (progress) => {
  if (progress >= 90) return '#67c23a'
  if (progress >= 60) return '#e6a23c'
  return '#f56c6c'
}

// 获取状态类型
const getStatusType = (status) => {
  const statusMap = {
    '待生产': 'info',
    '生产中': 'primary',
    '已完成': 'success',
    '已暂停': 'warning',
    '已取消': 'danger'
  }
  return statusMap[status] || 'info'
}

// 导出数据
const handleExport = () => {
  ElMessage.success('导出数据功能开发中')
}

// 创建工单
const handleCreateOrder = () => {
  ElMessage.success('创建工单功能开发中')
}

// 查看工单
const handleViewOrder = (order) => {
  ElMessage.info(`查看工单 ${order.orderNo} 详情功能开发中`)
}

// 编辑工单
const handleEditOrder = (order) => {
  ElMessage.info(`编辑工单 ${order.orderNo} 功能开发中`)
}

// 删除工单
const handleDeleteOrder = (order) => {
  ElMessage.info(`删除工单 ${order.orderNo} 功能开发中`)
}

// 监听图表周期变化
watch(chartPeriod, () => {
  initProductionChart()
})

// 监听窗口大小变化，调整图表尺寸
const handleResize = () => {
  productionChart.value?.resize()
  equipmentChart.value?.resize()
}

onMounted(() => {
  initProductionChart()
  initEquipmentChart()
  window.addEventListener('resize', handleResize)
})

// 组件卸载时清理
onBeforeUnmount(() => {
  window.removeEventListener('resize', handleResize)
  productionChart.value?.dispose()
  equipmentChart.value?.dispose()
})
</script>

<style scoped>
.dashboard-container {
  padding: 16px 0;
}

.page-header {
  margin-bottom: 20px;
  border-radius: 8px;
  background-color: #ffffff;
}

.page-title h1 {
  margin: 0;
  font-size: 24px;
  font-weight: bold;
  color: #1e293b;
}

.page-title p {
  margin: 8px 0 0 0;
  color: #64748b;
}

/* 统计卡片 */
.stats-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 16px;
  margin-bottom: 20px;
}

.stat-card {
  border-radius: 8px;
  background-color: #ffffff;
}

.stat-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
}

.stat-info {
  flex: 1;
}

.stat-label {
  margin: 0 0 8px 0;
  font-size: 14px;
  color: #64748b;
}

.stat-value {
  margin: 0 0 8px 0;
  font-size: 32px;
  font-weight: bold;
  color: #1e293b;
}

.stat-change {
  margin: 0;
  font-size: 12px;
  color: #64748b;
}

.stat-increase {
  color: #67c23a;
  font-weight: bold;
}

.stat-decrease {
  color: #f56c6c;
  font-weight: bold;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  color: #ffffff;
}

.orders-icon {
  background-color: #3b82f6;
}

.completion-icon {
  background-color: #67c23a;
}

.qualified-icon {
  background-color: #e6a23c;
}

.oee-icon {
  background-color: #f56c6c;
}

/* 图表区域 */
.charts-container {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(500px, 1fr));
  gap: 16px;
  margin-bottom: 20px;
}

.chart-card {
  border-radius: 8px;
  background-color: #ffffff;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-buttons {
  display: flex;
  gap: 10px;
}

.chart {
  height: 350px;
  width: 100%;
}

/* 表格区域 */
.table-card {
  border-radius: 8px;
  background-color: #ffffff;
}
</style>