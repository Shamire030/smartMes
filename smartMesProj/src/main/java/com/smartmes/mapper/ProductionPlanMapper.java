package com.smartmes.mapper;

import com.smartmes.model.ProductionPlan;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Date;

/**
 * 生产计划Mapper接口
 * 使用MyBatis实现数据访问层
 */
@Mapper
public interface ProductionPlanMapper {
    
    /**
     * 根据ID获取生产计划
     * @param id 计划ID
     * @return 生产计划对象
     */
    @Select("SELECT * FROM production_plan WHERE id = #{id}")
    ProductionPlan findById(Long id);
    
    /**
     * 根据计划编号查询计划
     * @param planCode 计划编号
     * @return 生产计划对象
     */
    @Select("SELECT * FROM production_plan WHERE plan_code = #{planCode}")
    ProductionPlan findByPlanCode(String planCode);
    
    /**
     * 获取所有生产计划
     * @return 生产计划列表
     */
    @Select("SELECT * FROM production_plan")
    List<ProductionPlan> findAll();
    
    /**
     * 根据产品ID查询计划列表
     * @param productId 产品ID
     * @return 生产计划列表
     */
    @Select("SELECT * FROM production_plan WHERE product_id = #{productId}")
    List<ProductionPlan> findByProductId(Long productId);
    
    /**
     * 根据状态查询计划列表
     * @param status 计划状态
     * @return 生产计划列表
     */
    @Select("SELECT * FROM production_plan WHERE status = #{status}")
    List<ProductionPlan> findByStatus(Integer status);
    
    /**
     * 根据时间范围查询计划列表
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 生产计划列表
     */
    @Select("SELECT * FROM production_plan WHERE plan_start_time BETWEEN #{startTime} AND #{endTime}")
    List<ProductionPlan> findByPlanStartTimeBetween(Date startTime, Date endTime);
    
    /**
     * 根据ERP订单号查询计划
     * @param erpOrderCode ERP订单号
     * @return 生产计划对象
     */
    @Select("SELECT * FROM production_plan WHERE erp_order_code = #{erpOrderCode}")
    ProductionPlan findByErpOrderCode(String erpOrderCode);
    
    /**
     * 新增生产计划
     * @param productionPlan 生产计划对象
     * @return 影响行数
     */
    @Insert("INSERT INTO production_plan(plan_code, plan_name, product_id, plan_quantity, completed_quantity, " +
            "plan_start_time, plan_end_time, actual_start_time, actual_end_time, status, responsible_person, " +
            "remark, create_time, update_time, erp_order_code) " +
            "VALUES(#{planCode}, #{planName}, #{productId}, #{planQuantity}, #{completedQuantity}, " +
            "#{planStartTime}, #{planEndTime}, #{actualStartTime}, #{actualEndTime}, #{status}, #{responsiblePerson}, " +
            "#{remark}, #{createTime}, #{updateTime}, #{erpOrderCode})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(ProductionPlan productionPlan);
    
    /**
     * 更新生产计划
     * @param productionPlan 生产计划对象
     * @return 影响行数
     */
    @Update("UPDATE production_plan SET plan_code = #{planCode}, plan_name = #{planName}, product_id = #{productId}, " +
            "plan_quantity = #{planQuantity}, completed_quantity = #{completedQuantity}, " +
            "plan_start_time = #{planStartTime}, plan_end_time = #{planEndTime}, actual_start_time = #{actualStartTime}, " +
            "actual_end_time = #{actualEndTime}, status = #{status}, responsible_person = #{responsiblePerson}, " +
            "remark = #{remark}, update_time = #{updateTime}, erp_order_code = #{erpOrderCode} WHERE id = #{id}")
    int update(ProductionPlan productionPlan);
    
    /**
     * 更新计划状态
     * @param id 计划ID
     * @param status 新状态
     * @param updateTime 更新时间
     * @return 影响行数
     */
    @Update("UPDATE production_plan SET status = #{status}, update_time = #{updateTime} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status, @Param("updateTime") Date updateTime);
    
    /**
     * 删除生产计划
     * @param id 计划ID
     * @return 影响行数
     */
    @Delete("DELETE FROM production_plan WHERE id = #{id}")
    int deleteById(Long id);
}