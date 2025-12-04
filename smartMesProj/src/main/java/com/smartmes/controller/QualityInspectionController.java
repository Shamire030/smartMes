package com.smartmes.controller;

import com.smartmes.model.QualityInspection;
import com.smartmes.service.QualityInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 质量管理控制器
 * 提供质量检查相关的REST API接口
 */
@RestController
@RequestMapping("/api/quality-inspections")
public class QualityInspectionController {
    
    @Autowired
    private QualityInspectionService qualityInspectionService;
    
    /**
     * 根据ID获取质检记录
     * @param id 质检记录ID
     * @return Result响应对象
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        QualityInspection inspection = qualityInspectionService.getById(id);
        if (inspection == null) {
            return Result.error("质检记录不存在");
        }
        return Result.success(inspection);
    }
    
    /**
     * 根据质检单号获取质检记录
     * @param code 质检单号
     * @return Result响应对象
     */
    @GetMapping("/code/{code}")
    public Result getByInspectionCode(@PathVariable("code") String inspectionCode) {
        QualityInspection inspection = qualityInspectionService.getByInspectionCode(inspectionCode);
        if (inspection == null) {
            return Result.error("质检记录不存在");
        }
        return Result.success(inspection);
    }
    
    /**
     * 根据质检类型获取质检记录列表
     * @param inspectionType 质检类型
     * @return Result响应对象
     */
    @GetMapping("/type/{inspectionType}")
    public Result getByInspectionType(@PathVariable Integer inspectionType) {
        List<QualityInspection> inspections = qualityInspectionService.getByInspectionType(inspectionType);
        return Result.success(inspections);
    }
    
    /**
     * 根据关联ID和质检类型获取质检记录列表
     * @param relatedId 关联ID
     * @param inspectionType 质检类型
     * @return Result响应对象
     */
    @GetMapping("/related")
    public Result getByRelatedId(@RequestParam Long relatedId, @RequestParam Integer inspectionType) {
        List<QualityInspection> inspections = qualityInspectionService.getByRelatedId(relatedId, inspectionType);
        return Result.success(inspections);
    }
    
    /**
     * 根据产品ID获取质检记录列表
     * @param productId 产品ID
     * @return Result响应对象
     */
    @GetMapping("/product/{productId}")
    public Result getByProductId(@PathVariable Long productId) {
        List<QualityInspection> inspections = qualityInspectionService.getByProductId(productId);
        return Result.success(inspections);
    }
    
    /**
     * 根据批次号获取质检记录列表
     * @param batchNo 批次号
     * @return Result响应对象
     */
    @GetMapping("/batch/{batchNo}")
    public Result getByBatchNo(@PathVariable String batchNo) {
        List<QualityInspection> inspections = qualityInspectionService.getByBatchNo(batchNo);
        return Result.success(inspections);
    }
    
    /**
     * 根据时间范围获取质检记录列表
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return Result响应对象
     */
    @GetMapping("/time-range")
    public Result getByTimeRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        List<QualityInspection> inspections = qualityInspectionService.getByTimeRange(startTime, endTime);
        return Result.success(inspections);
    }
    
    /**
     * 创建质检记录
     * @param inspection 质量检查对象
     * @return Result响应对象
     */
    @PostMapping
    public Result create(@RequestBody QualityInspection inspection) {
        try {
            QualityInspection createdInspection = qualityInspectionService.createQualityInspection(inspection);
            return Result.success(createdInspection);
        } catch (Exception e) {
            return Result.error("创建质检记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新质检记录
     * @param id 质检记录ID
     * @param inspection 质量检查对象
     * @return Result响应对象
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody QualityInspection inspection) {
        try {
            // 设置ID
            inspection.setId(id);
            QualityInspection updatedInspection = qualityInspectionService.updateQualityInspection(inspection);
            return Result.success(updatedInspection);
        } catch (Exception e) {
            return Result.error("更新质检记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除质检记录
     * @param id 质检记录ID
     * @return Result响应对象
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        try {
            boolean success = qualityInspectionService.deleteQualityInspection(id);
            if (success) {
                return Result.success("删除成功");
            } else {
                return Result.error("质检记录不存在或无法删除");
            }
        } catch (Exception e) {
            return Result.error("删除质检记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 开始质检
     * @param id 质检记录ID
     * @return Result响应对象
     */
    @PostMapping("/{id}/start")
    public Result startInspection(@PathVariable Long id) {
        try {
            QualityInspection inspection = qualityInspectionService.startInspection(id);
            return Result.success(inspection);
        } catch (Exception e) {
            return Result.error("开始质检失败: " + e.getMessage());
        }
    }
    
    /**
     * 完成质检
     * @param id 质检记录ID
     * @param qualifiedQuantity 合格数量
     * @param unqualifiedQuantity 不合格数量
     * @param inspectionResult 检验结果
     * @param unqualifiedReason 不合格原因
     * @return Result响应对象
     */
    @PostMapping("/{id}/complete")
    public Result completeInspection(
            @PathVariable Long id,
            @RequestParam Integer qualifiedQuantity,
            @RequestParam Integer unqualifiedQuantity,
            @RequestParam Integer inspectionResult,
            @RequestParam(required = false) String unqualifiedReason) {
        try {
            QualityInspection inspection = qualityInspectionService.completeInspection(
                    id, qualifiedQuantity, unqualifiedQuantity, inspectionResult, unqualifiedReason);
            return Result.success(inspection);
        } catch (Exception e) {
            return Result.error("完成质检失败: " + e.getMessage());
        }
    }
    
    /**
     * 生成质检单号
     * @param inspectionType 质检类型
     * @return Result响应对象
     */
    @GetMapping("/generate-code")
    public Result generateInspectionCode(@RequestParam Integer inspectionType) {
        String code = qualityInspectionService.generateInspectionCode(inspectionType);
        return Result.success(code);
    }
    
    /**
     * 获取质量统计数据
     * @param productId 产品ID（可选）
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return Result响应对象
     */
    @GetMapping("/statistics")
    public Result getQualityStatistics(
            @RequestParam(required = false) Long productId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {
        try {
            QualityInspectionService.QualityStatisticsDTO statistics = 
                    qualityInspectionService.getQualityStatistics(productId, startTime, endTime);
            return Result.success(statistics);
        } catch (Exception e) {
            return Result.error("获取质量统计数据失败: " + e.getMessage());
        }
    }
    
    /**
     * 统一响应结果类
     */
    static class Result {
        private int code;
        private String message;
        private Object data;
        
        private Result(int code, String message, Object data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }
        
        public static Result success(Object data) {
            return new Result(200, "success", data);
        }
        
        public static Result error(String message) {
            return new Result(500, message, null);
        }
        
        // Getters and Setters
        public int getCode() { return code; }
        public void setCode(int code) { this.code = code; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public Object getData() { return data; }
        public void setData(Object data) { this.data = data; }
    }
}
