package com.smartmes.controller;

import com.smartmes.model.EquipmentMaintenance;
import com.smartmes.service.EquipmentMaintenanceService;
import com.smartmes.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 设备维护保养控制器
 */
@RestController
@RequestMapping("/api/equipment-maintenance")
public class EquipmentMaintenanceController {
    
    @Autowired
    private EquipmentMaintenanceService equipmentMaintenanceService;
    
    /**
     * 根据ID查询维护保养记录
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        EquipmentMaintenance maintenance = equipmentMaintenanceService.selectById(id);
        return Result.success(maintenance);
    }
    
    /**
     * 根据保养单号查询维护保养记录
     */
    @GetMapping("/selectByMaintenanceCode/{maintenanceCode}")
    public Result selectByMaintenanceCode(@PathVariable String maintenanceCode) {
        EquipmentMaintenance maintenance = equipmentMaintenanceService.selectByMaintenanceCode(maintenanceCode);
        return Result.success(maintenance);
    }
    
    /**
     * 根据设备ID查询维护保养记录
     */
    @GetMapping("/selectByEquipmentId/{equipmentId}")
    public Result selectByEquipmentId(@PathVariable Integer equipmentId) {
        List<EquipmentMaintenance> maintenanceList = equipmentMaintenanceService.selectByEquipmentId(equipmentId);
        return Result.success(maintenanceList);
    }
    
    /**
     * 根据保养类型查询维护保养记录
     */
    @GetMapping("/selectByMaintenanceType/{maintenanceType}")
    public Result selectByMaintenanceType(@PathVariable String maintenanceType) {
        List<EquipmentMaintenance> maintenanceList = equipmentMaintenanceService.selectByMaintenanceType(maintenanceType);
        return Result.success(maintenanceList);
    }
    
    /**
     * 根据保养状态查询维护保养记录
     */
    @GetMapping("/selectByStatus/{status}")
    public Result selectByStatus(@PathVariable Integer status) {
        List<EquipmentMaintenance> maintenanceList = equipmentMaintenanceService.selectByStatus(status);
        return Result.success(maintenanceList);
    }
    
    /**
     * 根据保养负责人查询维护保养记录
     */
    @GetMapping("/selectByMaintenancePersonId/{maintenancePersonId}")
    public Result selectByMaintenancePersonId(@PathVariable Integer maintenancePersonId) {
        List<EquipmentMaintenance> maintenanceList = equipmentMaintenanceService.selectByMaintenancePersonId(maintenancePersonId);
        return Result.success(maintenanceList);
    }
    
    /**
     * 查询时间范围内的维护保养记录
     */
    @GetMapping("/selectByTimeRange")
    public Result selectByTimeRange(@RequestParam String startTime, @RequestParam String endTime) {
        List<EquipmentMaintenance> maintenanceList = equipmentMaintenanceService.selectByTimeRange(startTime, endTime);
        return Result.success(maintenanceList);
    }
    
    /**
     * 查询设备在指定时间范围内的维护保养记录
     */
    @GetMapping("/selectByEquipmentIdAndTimeRange")
    public Result selectByEquipmentIdAndTimeRange(@RequestParam Integer equipmentId, 
                                               @RequestParam String startTime, 
                                               @RequestParam String endTime) {
        List<EquipmentMaintenance> maintenanceList = equipmentMaintenanceService.selectByEquipmentIdAndTimeRange(equipmentId, startTime, endTime);
        return Result.success(maintenanceList);
    }
    
    /**
     * 分页查询维护保养记录
     */
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum, 
                           @RequestParam(defaultValue = "10") Integer pageSize) {
        List<EquipmentMaintenance> maintenanceList = equipmentMaintenanceService.selectPage(pageNum, pageSize);
        Integer total = equipmentMaintenanceService.selectCount();
        Map<String, Object> result = new java.util.HashMap<>();
        result.put("list", maintenanceList);
        result.put("total", total);
        return Result.success(result);
    }
    
    /**
     * 查询所有维护保养记录
     */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<EquipmentMaintenance> maintenanceList = equipmentMaintenanceService.selectAll();
        return Result.success(maintenanceList);
    }
    
    /**
     * 根据条件查询维护保养记录
     */
    @PostMapping("/selectByParams")
    public Result selectByParams(@RequestBody Map<String, Object> params) {
        List<EquipmentMaintenance> maintenanceList = equipmentMaintenanceService.selectByParams(params);
        return Result.success(maintenanceList);
    }
    
    /**
     * 创建维护保养记录
     */
    @PostMapping("/insert")
    public Result insert(@RequestBody EquipmentMaintenance maintenance) {
        Integer result = equipmentMaintenanceService.insert(maintenance);
        return result > 0 ? Result.success() : Result.error("创建失败");
    }
    
    /**
     * 更新维护保养记录
     */
    @PostMapping("/update")
    public Result update(@RequestBody EquipmentMaintenance maintenance) {
        Integer result = equipmentMaintenanceService.update(maintenance);
        return result > 0 ? Result.success() : Result.error("更新失败");
    }
    
    /**
     * 删除维护保养记录
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        Integer result = equipmentMaintenanceService.deleteById(id);
        return result > 0 ? Result.success() : Result.error("删除失败");
    }
    
    /**
     * 更新保养状态
     */
    @PostMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Integer id, @PathVariable Integer status) {
        Integer result = equipmentMaintenanceService.updateStatus(id, status);
        return result > 0 ? Result.success() : Result.error("更新失败");
    }
    
    /**
     * 生成保养单号
     */
    @GetMapping("/generateMaintenanceCode")
    public Result generateMaintenanceCode() {
        String maintenanceCode = equipmentMaintenanceService.generateMaintenanceCode();
        return Result.success(maintenanceCode);
    }
    
    /**
     * 获取保养状态统计
     */
    @GetMapping("/getMaintenanceStatusStatistics")
    public Result getMaintenanceStatusStatistics() {
        Map<String, Integer> statistics = equipmentMaintenanceService.getMaintenanceStatusStatistics();
        return Result.success(statistics);
    }
    
    /**
     * 获取保养类型统计
     */
    @GetMapping("/getMaintenanceTypeStatistics")
    public Result getMaintenanceTypeStatistics() {
        Map<String, Integer> statistics = equipmentMaintenanceService.getMaintenanceTypeStatistics();
        return Result.success(statistics);
    }
    
    /**
     * 获取设备保养频次统计
     */
    @GetMapping("/getEquipmentMaintenanceFrequencyStatistics")
    public Result getEquipmentMaintenanceFrequencyStatistics() {
        Map<Integer, Integer> statistics = equipmentMaintenanceService.getEquipmentMaintenanceFrequencyStatistics();
        return Result.success(statistics);
    }
    
    /**
     * 开始保养
     */
    @PostMapping("/startMaintenance/{id}")
    public Result startMaintenance(@PathVariable Integer id, 
                                 @RequestParam Integer operatorId, 
                                 @RequestParam String operatorName) {
        Integer result = equipmentMaintenanceService.startMaintenance(id, operatorId, operatorName);
        return result > 0 ? Result.success() : Result.error("开始保养失败");
    }
    
    /**
     * 完成保养
     */
    @PostMapping("/completeMaintenance/{id}")
    public Result completeMaintenance(@PathVariable Integer id, 
                                    @RequestParam String maintenanceResult, 
                                    @RequestParam String discoveredIssues, 
                                    @RequestParam String handlingMeasures) {
        Integer result = equipmentMaintenanceService.completeMaintenance(id, maintenanceResult, discoveredIssues, handlingMeasures);
        return result > 0 ? Result.success() : Result.error("完成保养失败");
    }
    
    /**
     * 验收保养
     */
    @PostMapping("/verifyMaintenance/{id}")
    public Result verifyMaintenance(@PathVariable Integer id, 
                                  @RequestParam Integer verifierId, 
                                  @RequestParam String verifierName) {
        Integer result = equipmentMaintenanceService.verifyMaintenance(id, verifierId, verifierName);
        return result > 0 ? Result.success() : Result.error("验收保养失败");
    }
}
