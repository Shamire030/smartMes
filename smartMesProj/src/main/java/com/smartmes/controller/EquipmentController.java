package com.smartmes.controller;

import com.smartmes.model.Equipment;
import com.smartmes.service.EquipmentService;
import com.smartmes.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * 设备管理控制器
 */
@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {
    
    @Autowired
    private EquipmentService equipmentService;
    
    /**
     * 根据ID查询设备信息
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Equipment equipment = equipmentService.selectById(id);
        return Result.success(equipment);
    }
    
    /**
     * 根据编码查询设备信息
     */
    @GetMapping("/selectByEquipmentCode/{equipmentCode}")
    public Result selectByEquipmentCode(@PathVariable String equipmentCode) {
        Equipment equipment = equipmentService.selectByEquipmentCode(equipmentCode);
        return Result.success(equipment);
    }
    
    /**
     * 根据名称查询设备信息
     */
    @GetMapping("/selectByEquipmentName/{equipmentName}")
    public Result selectByEquipmentName(@PathVariable String equipmentName) {
        List<Equipment> equipmentList = equipmentService.selectByEquipmentName(equipmentName);
        return Result.success(equipmentList);
    }
    
    /**
     * 根据类型查询设备信息
     */
    @GetMapping("/selectByEquipmentType/{equipmentType}")
    public Result selectByEquipmentType(@PathVariable String equipmentType) {
        List<Equipment> equipmentList = equipmentService.selectByEquipmentType(equipmentType);
        return Result.success(equipmentList);
    }
    
    /**
     * 根据状态查询设备信息
     */
    @GetMapping("/selectByStatus/{status}")
    public Result selectByStatus(@PathVariable Integer status) {
        List<Equipment> equipmentList = equipmentService.selectByStatus(status);
        return Result.success(equipmentList);
    }
    
    /**
     * 根据区域查询设备信息
     */
    @GetMapping("/selectByLocation/{location}")
    public Result selectByLocation(@PathVariable String location) {
        List<Equipment> equipmentList = equipmentService.selectByLocation(location);
        return Result.success(equipmentList);
    }
    
    /**
     * 根据负责人查询设备信息
     */
    @GetMapping("/selectByResponsiblePersonId/{responsiblePersonId}")
    public Result selectByResponsiblePersonId(@PathVariable Integer responsiblePersonId) {
        List<Equipment> equipmentList = equipmentService.selectByResponsiblePersonId(responsiblePersonId);
        return Result.success(equipmentList);
    }
    
    /**
     * 根据在线状态查询设备信息
     */
    @GetMapping("/selectByOnlineStatus/{onlineStatus}")
    public Result selectByOnlineStatus(@PathVariable Integer onlineStatus) {
        List<Equipment> equipmentList = equipmentService.selectByOnlineStatus(onlineStatus);
        return Result.success(equipmentList);
    }
    
    /**
     * 分页查询设备信息
     */
    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum, 
                           @RequestParam(defaultValue = "10") Integer pageSize) {
        List<Equipment> equipmentList = equipmentService.selectPage(pageNum, pageSize);
        Integer total = equipmentService.selectCount();
        Map<String, Object> result = new java.util.HashMap<>();
        result.put("list", equipmentList);
        result.put("total", total);
        return Result.success(result);
    }
    
    /**
     * 查询所有设备信息
     */
    @GetMapping("/selectAll")
    public Result selectAll() {
        List<Equipment> equipmentList = equipmentService.selectAll();
        return Result.success(equipmentList);
    }
    
    /**
     * 根据条件查询设备信息
     */
    @PostMapping("/selectByParams")
    public Result selectByParams(@RequestBody Map<String, Object> params) {
        List<Equipment> equipmentList = equipmentService.selectByParams(params);
        return Result.success(equipmentList);
    }
    
    /**
     * 创建设备信息
     */
    @PostMapping("/insert")
    public Result insert(@RequestBody Equipment equipment) {
        Integer result = equipmentService.insert(equipment);
        return result > 0 ? Result.success() : Result.error("创建失败");
    }
    
    /**
     * 更新设备信息
     */
    @PostMapping("/update")
    public Result update(@RequestBody Equipment equipment) {
        Integer result = equipmentService.update(equipment);
        return result > 0 ? Result.success() : Result.error("更新失败");
    }
    
    /**
     * 删除设备信息
     */
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        Integer result = equipmentService.deleteById(id);
        return result > 0 ? Result.success() : Result.error("删除失败");
    }
    
    /**
     * 更新设备状态
     */
    @PostMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Integer id, @PathVariable Integer status) {
        Integer result = equipmentService.updateStatus(id, status);
        return result > 0 ? Result.success() : Result.error("更新失败");
    }
    
    /**
     * 更新设备在线状态
     */
    @PostMapping("/updateOnlineStatus/{id}/{onlineStatus}")
    public Result updateOnlineStatus(@PathVariable Integer id, @PathVariable Integer onlineStatus) {
        Integer result = equipmentService.updateOnlineStatus(id, onlineStatus);
        return result > 0 ? Result.success() : Result.error("更新失败");
    }
    
    /**
     * 更新设备运行时间
     */
    @PostMapping("/updateRuntime/{id}/{runtime}")
    public Result updateRuntime(@PathVariable Integer id, @PathVariable Integer runtime) {
        Integer result = equipmentService.updateRuntime(id, runtime);
        return result > 0 ? Result.success() : Result.error("更新失败");
    }
    
    /**
     * 更新设备责任人
     */
    @PostMapping("/updateResponsiblePerson")
    public Result updateResponsiblePerson(@RequestParam Integer id, 
                                        @RequestParam Integer responsiblePersonId, 
                                        @RequestParam String responsiblePersonName) {
        Integer result = equipmentService.updateResponsiblePerson(id, responsiblePersonId, responsiblePersonName);
        return result > 0 ? Result.success() : Result.error("更新失败");
    }
    
    /**
     * 生成设备编码
     */
    @GetMapping("/generateEquipmentCode")
    public Result generateEquipmentCode() {
        String equipmentCode = equipmentService.generateEquipmentCode();
        return Result.success(equipmentCode);
    }
    
    /**
     * 获取设备状态统计
     */
    @GetMapping("/getEquipmentStatusStatistics")
    public Result getEquipmentStatusStatistics() {
        Map<String, Integer> statistics = equipmentService.getEquipmentStatusStatistics();
        return Result.success(statistics);
    }
    
    /**
     * 获取设备类型统计
     */
    @GetMapping("/getEquipmentTypeStatistics")
    public Result getEquipmentTypeStatistics() {
        Map<String, Integer> statistics = equipmentService.getEquipmentTypeStatistics();
        return Result.success(statistics);
    }
    
    /**
     * 获取设备区域统计
     */
    @GetMapping("/getEquipmentLocationStatistics")
    public Result getEquipmentLocationStatistics() {
        Map<String, Integer> statistics = equipmentService.getEquipmentLocationStatistics();
        return Result.success(statistics);
    }
}
