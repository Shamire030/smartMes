package com.smartmes.model;

import lombok.Data;
import java.util.Date;

/**
 * 设备实体类
 * 对应第四组（设备管理服务）的核心业务模型
 * 支持设备信息管理、状态监控和维护保养
 */
@Data
public class Equipment {
    
    /**
     * 设备ID
     */
    private Long id;
    
    /**
     * 设备编码
     */
    private String equipmentCode;
    
    /**
     * 设备名称
     */
    private String equipmentName;
    
    /**
     * 设备型号
     */
    private String equipmentModel;
    
    /**
     * 设备类型ID
     */
    private Long equipmentTypeId;
    
    /**
     * 设备类型名称
     */
    private String equipmentTypeName;
    
    /**
     * 所在工厂ID
     */
    private Long factoryId;
    
    /**
     * 所在车间ID
     */
    private Long workshopId;
    
    /**
     * 所在产线ID
     */
    private Long productionLineId;
    
    /**
     * 设备状态：1-正常运行 2-待机 3-故障 4-保养中 5-维修中 6-停用
     */
    private Integer status;
    
    /**
     * 运行状态：0-离线 1-在线
     */
    private Integer onlineStatus;
    
    /**
     * 设备位置
     */
    private String location;
    
    /**
     * 负责人ID
     */
    private Long responsiblePersonId;
    
    /**
     * 负责人姓名
     */
    private String responsiblePersonName;
    
    /**
     * 制造商
     */
    private String manufacturer;
    
    /**
     * 供应商
     */
    private String supplier;
    
    /**
     * 购买日期
     */
    private Date purchaseDate;
    
    /**
     * 安装日期
     */
    private Date installationDate;
    
    /**
     * 预计使用年限
     */
    private Integer expectedUseYears;
    
    /**
     * 累计运行时间（小时）
     */
    private Double totalRunningHours;
    
    /**
     * 设备价值
     */
    private Double equipmentValue;
    
    /**
     * 设备描述
     */
    private String description;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
    /**
     * 最后状态更新时间
     */
    private Date lastStatusUpdateTime;
    
    /**
     * 最后在线时间
     */
    private Date lastOnlineTime;
}
