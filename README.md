# Smart MES - 智能制造执行系统（原型！）

[![Build Status](https://img.shields.io/badge/build-passing-green.svg)](https://github.com/Shamire030/smartMes)

Smart MES 是一款基于现代信息技术的智能制造执行系统，旨在帮助制造企业实现生产过程的数字化、可视化和智能化管理。

---

## 🚀 功能特性

### 核心模块

| 模块 | 功能描述 |
|------|----------|
| 🏭 **生产计划** | 生产计划的创建、编辑、查询和状态管理 |
| ⚙️ **生产执行** | 实时监控生产进度、产量统计、工序管理 |
| ✅ **质量管理** | 质检记录、质量分析、异常处理 |
| 🛠️ **设备管理** | 设备台账、维护计划、设备状态监控 |
| 👥 **用户管理** | 用户信息、角色权限、访问控制 |

### 技术优势

- **前后端分离**：Vue 3 + Spring Boot 架构
- **实时数据**：支持实时生产数据展示
- **可视化报表**：基于 ECharts 的数据分析图表
- **安全认证**：JWT 身份验证与授权
- **响应式设计**：支持多种终端设备

---

## 🛠️ 技术栈

### 前端技术

| 技术 | 版本 | 说明 |
|------|------|------|
| Vue | 3.x | 前端框架 |
| Vite | 6.x | 构建工具 |
| Element Plus | 2.x | UI组件库 |
| ECharts | 6.x | 图表库 |
| Pinia | 2.x | 状态管理 |
| Vue Router | 4.x | 路由管理 |
| Axios | 1.x | HTTP客户端 |

### 后端技术

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 2.7.x | 后端框架 |
| MyBatis | 3.5.x | ORM框架 |
| MySQL | 8.0.x | 数据库 |
| Druid | 1.2.x | 连接池 |
| SpringDoc | 1.6.x | API文档 |
| JWT | 0.9.x | 身份认证 |

---

## 📦 快速开始

### 环境要求

- Java 1.8+
- Node.js 18+
- MySQL 8.0+
- Maven 3.8+

### 启动步骤

#### 1. 数据库配置

创建数据库并配置连接信息：

```sql
CREATE DATABASE smartmes CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 2. 后端启动

```bash
cd smartMesProj
mvn clean package -DskipTests
java -jar target/smartmes-proj-1.0.0.jar
```

#### 3. 前端启动

```bash
cd front-end
npm install
npm run dev
```

#### 4. 访问地址

| 服务 | 地址 |
|------|------|
| 前端应用 | http://localhost:5173 |
| 后端API | http://localhost:8081/smartmes/api |
| Swagger文档 | http://localhost:8081/smartmes/swagger-ui.html |

---

## 📁 项目结构

```
smartMes/
├── front-end/                    # 前端项目
│   ├── src/
│   │   ├── api/                  # API请求封装
│   │   ├── components/           # 通用组件
│   │   ├── views/                # 页面组件
│   │   ├── stores/               # 状态管理
│   │   └── router/               # 路由配置
│   └── package.json
├── smartMesProj/                 # 后端项目
│   ├── src/main/java/com/smartmes/
│   │   ├── controller/           # 控制器
│   │   ├── service/              # 业务逻辑
│   │   ├── mapper/               # 数据访问
│   │   ├── model/                # 数据模型
│   │   └── config/               # 配置类
│   └── pom.xml
├── start-all.ps1                 # 一键启动脚本
├── GIT_GUIDE.md                  # Git操作指南
└── README.md                     # 项目说明
```

---

## 🔒 默认账号

| 角色 | 用户名 | 密码 | 权限说明 |
|------|--------|------|----------|
| 超级管理员 | admin | 123456 | 全部权限 |
| 管理员 | manager | 123456 | 管理权限 |
| 操作员 | operator | 123456 | 操作权限 |
| 只读用户 | viewer | 123456 | 查看权限 |

---

## 📖 API 文档

启动后端服务后，访问 Swagger UI 查看完整的 API 文档：

```
http://localhost:8081/smartmes/swagger-ui.html
```

---

## 🤝 贡献指南

欢迎贡献代码！请遵循以下步骤：

1. Fork 本仓库
2. 创建特性分支：`git checkout -b feature/xxx`
3. 提交代码：`git commit -m "feat: 添加xxx功能"`
4. 推送到分支：`git push origin feature/xxx`
5. 创建 Pull Request

---

##  联系方式

好吧这只是一个实训项目，除非你真的很需要或者想找个游戏搭子：

- QQ：2397498718
- GitHub Issues：https://github.com/Shamire030/smartMes/issues