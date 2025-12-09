import request from './request'

// 获取所有生产计划
export const getProductionPlansApi = () => {
  return request.get('/production-plans')
}

// 获取单个生产计划
export const getProductionPlanApi = (id) => {
  return request.get(`/production-plans/${id}`)
}

// 创建生产计划
export const createProductionPlanApi = (planData) => {
  return request.post('/production-plans', planData)
}

// 更新生产计划
export const updateProductionPlanApi = (id, planData) => {
  return request.put(`/production-plans/${id}`, planData)
}

// 删除生产计划
export const deleteProductionPlanApi = (id) => {
  return request.delete(`/production-plans/${id}`)
}

// 更新生产计划状态
export const updateProductionPlanStatusApi = (id, status) => {
  return request.put(`/production-plans/${id}/status`, { status })
}

// 按时间范围获取生产计划
export const getProductionPlansByTimeRangeApi = (startTime, endTime) => {
  return request.get('/production-plans/time-range', { params: { startTime, endTime } })
}

// 按状态获取生产计划
export const getProductionPlansByStatusApi = (status) => {
  return request.get(`/production-plans/status/${status}`)
}

// 按计划编号获取生产计划
export const getProductionPlanByCodeApi = (planCode) => {
  return request.get(`/production-plans/code/${planCode}`)
}