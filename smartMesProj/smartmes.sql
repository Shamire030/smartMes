-- 智能MES系统数据库创建脚本
-- 创建时间: 2025-12-12

-- 1. 创建数据库
CREATE DATABASE IF NOT EXISTS smartmes CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE smartmes;

-- 2. 创建用户表
CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    name VARCHAR(50) COMMENT '真实姓名',
    role VARCHAR(20) NOT NULL COMMENT '角色：admin, operator, manager',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '电话',
    status INT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 3. 创建设备表
CREATE TABLE IF NOT EXISTS equipment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '设备ID',
    equipment_code VARCHAR(50) NOT NULL UNIQUE COMMENT '设备编码',
    equipment_name VARCHAR(100) NOT NULL COMMENT '设备名称',
    equipment_type VARCHAR(50) COMMENT '设备类型',
    model VARCHAR(50) COMMENT '设备型号',
    manufacturer VARCHAR(100) COMMENT '制造商',
    purchase_date DATE COMMENT '购买日期',
    installation_date DATE COMMENT '安装日期',
    warranty_period VARCHAR(20) COMMENT '保修期',
    location VARCHAR(100) COMMENT '设备位置',
    status INT DEFAULT 1 COMMENT '设备状态：0-停用，1-正常，2-维修中，3-故障',
    responsible_person VARCHAR(50) COMMENT '负责人',
    ip_address VARCHAR(20) COMMENT '设备IP地址',
    mac_address VARCHAR(20) COMMENT '设备MAC地址',
    description TEXT COMMENT '设备描述',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    equipment_category VARCHAR(50) COMMENT '设备类别',
    production_line_id BIGINT COMMENT '所属生产线ID',
    asset_value DECIMAL(15,2) COMMENT '设备价值(元)',
    last_maintenance_date DATE COMMENT '上次维护日期',
    next_maintenance_date DATE COMMENT '下次维护日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备表';

-- 4. 创建设备数据表
CREATE TABLE IF NOT EXISTS equipment_data (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '数据ID',
    equipment_id BIGINT NOT NULL COMMENT '设备ID',
    equipment_code VARCHAR(50) NOT NULL COMMENT '设备编码',
    equipment_name VARCHAR(100) NOT NULL COMMENT '设备名称',
    data_type INT NOT NULL COMMENT '数据类型：1-运行数据 2-状态数据 3-告警数据',
    data_key VARCHAR(50) NOT NULL COMMENT '数据键名',
    data_value VARCHAR(100) NOT NULL COMMENT '数据值',
    unit VARCHAR(20) COMMENT '单位',
    data_time DATETIME NOT NULL COMMENT '数据采集时间',
    status INT DEFAULT 1 COMMENT '状态：0-无效 1-有效',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    equipment_category VARCHAR(50) COMMENT '设备类别',
    production_line_id BIGINT COMMENT '所属生产线ID',
    production_line_name VARCHAR(100) COMMENT '生产线名称',
    shift VARCHAR(20) COMMENT '班次',
    operator_id BIGINT COMMENT '操作员ID',
    operator_name VARCHAR(50) COMMENT '操作员姓名',
    remark VARCHAR(200) COMMENT '备注',
    runtime_hours DOUBLE COMMENT '累计运行小时数',
    utilization_rate DOUBLE COMMENT '设备利用率(%)',
    oee DOUBLE COMMENT '设备综合效率(%)',
    temperature DOUBLE COMMENT '温度(℃)',
    pressure DOUBLE COMMENT '压力(MPa)',
    vibration DOUBLE COMMENT '振动值',
    speed DOUBLE COMMENT '速度(rpm)',
    energy_consumption DOUBLE COMMENT '能耗(kWh)',
    FOREIGN KEY (equipment_id) REFERENCES equipment(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备数据表';

-- 5. 创建设备维护保养表
CREATE TABLE IF NOT EXISTS equipment_maintenance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '保养记录ID',
    maintenance_code VARCHAR(50) NOT NULL UNIQUE COMMENT '保养单号',
    equipment_id BIGINT NOT NULL COMMENT '设备ID',
    equipment_code VARCHAR(50) NOT NULL COMMENT '设备编码',
    equipment_name VARCHAR(100) NOT NULL COMMENT '设备名称',
    maintenance_type INT NOT NULL COMMENT '保养类型：1-定期保养 2-预防性保养 3-故障后保养 4-专项保养',
    maintenance_plan_id BIGINT COMMENT '保养计划ID',
    plan_start_time DATETIME COMMENT '计划开始时间',
    plan_end_time DATETIME COMMENT '计划结束时间',
    actual_start_time DATETIME COMMENT '实际开始时间',
    actual_end_time DATETIME COMMENT '实际结束时间',
    status INT DEFAULT 0 COMMENT '保养状态：0-未开始 1-进行中 2-已完成 3-已取消',
    maintenance_content TEXT NOT NULL COMMENT '保养内容描述',
    maintenance_result INT COMMENT '保养结果：1-良好 2-一般 3-较差',
    discovered_issues TEXT COMMENT '发现的问题',
    handling_measures TEXT COMMENT '处理措施',
    maintenance_person_id BIGINT COMMENT '保养负责人ID',
    maintenance_person_name VARCHAR(50) COMMENT '保养负责人姓名',
    verifier_id BIGINT COMMENT '验收人ID',
    verifier_name VARCHAR(50) COMMENT '验收人姓名',
    verify_time DATETIME COMMENT '验收时间',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    FOREIGN KEY (equipment_id) REFERENCES equipment(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备维护保养记录';

-- 6. 创建生产计划表
CREATE TABLE IF NOT EXISTS production_plan (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '计划ID',
    plan_code VARCHAR(50) NOT NULL UNIQUE COMMENT '计划编号',
    plan_name VARCHAR(100) NOT NULL COMMENT '计划名称',
    product_id BIGINT NOT NULL COMMENT '产品ID',
    plan_quantity INT NOT NULL COMMENT '计划数量',
    completed_quantity INT DEFAULT 0 COMMENT '已完成数量',
    plan_start_time DATETIME NOT NULL COMMENT '计划开始时间',
    plan_end_time DATETIME NOT NULL COMMENT '计划结束时间',
    actual_start_time DATETIME COMMENT '实际开始时间',
    actual_end_time DATETIME COMMENT '实际结束时间',
    status INT DEFAULT 0 COMMENT '状态：0-未开始 1-进行中 2-已完成 3-已暂停 4-已取消',
    responsible_person VARCHAR(50) COMMENT '负责人',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    erp_order_code VARCHAR(50) COMMENT 'ERP订单号'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生产计划表';

-- 7. 创建生产执行表
CREATE TABLE IF NOT EXISTS production_execution (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '执行记录ID',
    plan_id BIGINT NOT NULL COMMENT '计划ID',
    product_id BIGINT NOT NULL COMMENT '产品ID',
    batch_no VARCHAR(50) NOT NULL COMMENT '批次号',
    equipment_id BIGINT NOT NULL COMMENT '设备ID',
    operator_id BIGINT NOT NULL COMMENT '操作员ID',
    start_time DATETIME NOT NULL COMMENT '开工时间',
    end_time DATETIME COMMENT '完工时间',
    plan_quantity INT NOT NULL COMMENT '计划数量',
    actual_quantity INT DEFAULT 0 COMMENT '实际产出数量',
    qualified_quantity INT DEFAULT 0 COMMENT '合格数量',
    unqualified_quantity INT DEFAULT 0 COMMENT '不合格数量',
    status INT DEFAULT 0 COMMENT '执行状态：0-未开始 1-进行中 2-已完成 3-已暂停',
    work_order_status INT DEFAULT 0 COMMENT '工单状态：0-正常 1-异常',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    FOREIGN KEY (plan_id) REFERENCES production_plan(id) ON DELETE CASCADE,
    FOREIGN KEY (equipment_id) REFERENCES equipment(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生产执行表';

-- 8. 创建质量检查表
CREATE TABLE IF NOT EXISTS quality_inspection (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '质检记录ID',
    inspection_code VARCHAR(50) NOT NULL UNIQUE COMMENT '质检单号',
    inspection_type INT NOT NULL COMMENT '质检类型：1-IQC（来料检验） 2-IPQC（过程检验） 3-FQC（成品检验）',
    related_id BIGINT COMMENT '关联ID（根据质检类型关联不同表）',
    product_id BIGINT NOT NULL COMMENT '产品ID',
    batch_no VARCHAR(50) NOT NULL COMMENT '批次号',
    inspection_quantity INT NOT NULL COMMENT '检验数量',
    qualified_quantity INT NOT NULL COMMENT '合格数量',
    unqualified_quantity INT NOT NULL COMMENT '不合格数量',
    defective_rate DOUBLE NOT NULL COMMENT '不良品率',
    inspection_result INT DEFAULT 0 COMMENT '检验结果：0-待检验 1-合格 2-不合格 3-让步接收',
    inspection_standard_id BIGINT COMMENT '检验标准ID',
    inspector_id BIGINT NOT NULL COMMENT '检验员ID',
    start_time DATETIME NOT NULL COMMENT '检验开始时间',
    end_time DATETIME COMMENT '检验结束时间',
    unqualified_reason TEXT COMMENT '不合格原因分析',
    processing_suggestion TEXT COMMENT '处理建议',
    remark VARCHAR(200) COMMENT '备注',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='质量检查表';

-- 9. 创建生产数据表
CREATE TABLE IF NOT EXISTS production_data (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    data_type VARCHAR(20) NOT NULL COMMENT '数据类型：日/周/月/季度/年统计',
    period VARCHAR(20) NOT NULL COMMENT '统计周期标识',
    plan_id INT COMMENT '生产计划ID',
    plan_code VARCHAR(50) COMMENT '生产计划编码',
    product_id INT NOT NULL COMMENT '产品ID',
    product_code VARCHAR(50) COMMENT '产品编码',
    product_name VARCHAR(100) COMMENT '产品名称',
    work_order_id INT COMMENT '工单ID',
    work_order_code VARCHAR(50) COMMENT '工单号',
    planned_quantity INT COMMENT '计划产量',
    actual_quantity INT COMMENT '实际产量',
    qualified_quantity INT COMMENT '合格数量',
    defective_quantity INT COMMENT '不良数量',
    pass_rate DOUBLE COMMENT '合格率',
    productivity_rate DOUBLE COMMENT '生产效率',
    total_man_hours INT COMMENT '总工时',
    equipment_runtime INT COMMENT '设备运行时间(分钟)',
    equipment_stop_time INT COMMENT '设备停机时间(分钟)',
    start_count INT COMMENT '开机次数',
    completed_count INT COMMENT '完成次数',
    abnormal_count INT COMMENT '异常次数',
    average_cycle_time DOUBLE COMMENT '平均周期时间(秒)',
    shift VARCHAR(20) COMMENT '班次',
    data_date DATETIME NOT NULL COMMENT '数据日期',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生产数据表';

-- 10. 创建质量数据表
CREATE TABLE IF NOT EXISTS quality_data (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
    data_type VARCHAR(20) NOT NULL COMMENT '数据类型：日/周/月/季度/年统计',
    period VARCHAR(20) NOT NULL COMMENT '统计周期标识',
    inspection_id INT COMMENT '质检记录ID',
    inspection_code VARCHAR(50) COMMENT '质检单号',
    product_id INT NOT NULL COMMENT '产品ID',
    product_code VARCHAR(50) COMMENT '产品编码',
    product_name VARCHAR(100) COMMENT '产品名称',
    inspection_type VARCHAR(20) NOT NULL COMMENT '质检类型：IQC/IPQC/FQC',
    inspected_quantity INT COMMENT '检验数量',
    qualified_quantity INT COMMENT '合格数量',
    defective_quantity INT COMMENT '不合格数量',
    pass_rate DOUBLE COMMENT '合格率',
    minor_defect_count INT COMMENT '轻微缺陷数量',
    major_defect_count INT COMMENT '严重缺陷数量',
    critical_defect_count INT COMMENT '致命缺陷数量',
    defect_reason VARCHAR(200) COMMENT '主要缺陷原因',
    equipment_id INT COMMENT '设备ID',
    equipment_code VARCHAR(50) COMMENT '设备编码',
    work_station_id INT COMMENT '工位ID',
    work_station_code VARCHAR(50) COMMENT '工位编码',
    operator_id INT COMMENT '操作人ID',
    operator_name VARCHAR(50) COMMENT '操作人姓名',
    inspect_person_id INT COMMENT '检验人员ID',
    inspect_person_name VARCHAR(50) COMMENT '检验人员姓名',
    lot_number VARCHAR(50) COMMENT '批次号',
    supplier_code VARCHAR(50) COMMENT '供应商编码',
    data_date DATETIME NOT NULL COMMENT '数据日期',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='质量数据表';

-- 11. 插入初始数据

-- 插入管理员用户
INSERT INTO user (username, password, name, role, status, create_time, update_time) 
VALUES ('admin', '$2a$10$e4hYxq8TQaV7xW4xK5zL6yM7nO8pQ9rS0tU1vW2xY3zA4bC5dE6f', '管理员', 'admin', 1, NOW(), NOW());

-- 插入测试操作员用户
INSERT INTO user (username, password, name, role, status, create_time, update_time) 
VALUES ('operator', '$2a$10$e4hYxq8TQaV7xW4xK5zL6yM7nO8pQ9rS0tU1vW2xY3zA4bC5dE6f', '操作员', 'operator', 1, NOW(), NOW());

-- 插入测试设备
INSERT INTO equipment (equipment_code, equipment_name, equipment_type, model, manufacturer, location, status, create_time, update_time) 
VALUES 
('EQ-001', '注塑机A', '注塑设备', 'IM-1200', '海天', '生产车间1', 1, NOW(), NOW()),
('EQ-002', '包装机B', '包装设备', 'PK-800', '永创', '生产车间2', 1, NOW(), NOW()),
('EQ-003', '传送带C', '输送设备', 'CV-500', '德马', '生产车间3', 1, NOW(), NOW());

-- 插入测试设备数据
INSERT INTO equipment_data (equipment_id, equipment_code, equipment_name, data_type, data_key, data_value, data_time, create_time, update_time) 
VALUES 
(1, 'EQ-001', '注塑机A', 1, 'temperature', '180', NOW(), NOW(), NOW()),
(1, 'EQ-001', '注塑机A', 1, 'pressure', '120', NOW(), NOW(), NOW()),
(2, 'EQ-002', '包装机B', 1, 'speed', '1200', NOW(), NOW(), NOW());

-- 12. 创建索引
CREATE INDEX idx_equipment_code ON equipment(equipment_code);
CREATE INDEX idx_equipment_status ON equipment(status);
CREATE INDEX idx_equipment_data_equipment_id ON equipment_data(equipment_id);
CREATE INDEX idx_production_execution_plan_id ON production_execution(plan_id);
CREATE INDEX idx_production_execution_equipment_id ON production_execution(equipment_id);
CREATE INDEX idx_quality_inspection_batch_no ON quality_inspection(batch_no);

-- 数据库创建完成提示
SELECT '智能MES系统数据库创建完成！' AS message;
