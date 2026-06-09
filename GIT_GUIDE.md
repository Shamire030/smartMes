# Git 操作指南

## 📋 目录

1. [基础配置](#1-基础配置)
2. [分支管理](#2-分支管理)
3. [代码提交](#3-代码提交)
4. [远程操作](#4-远程操作)
5. [分支合并](#5-分支合并)
6. [常见问题](#6-常见问题)

---

## 1. 基础配置

```bash
# 配置用户名
git config --global user.name "Your Name"

# 配置邮箱
git config --global user.email "your.email@example.com"

# 查看配置
git config --list
```

---

## 2. 分支管理

### 创建分支

```bash
# 基于当前分支创建新分支
git checkout -b feature-branch

# 基于指定分支创建新分支
git checkout -b feature-branch origin/main
```

### 查看分支

```bash
# 查看本地分支
git branch

# 查看所有分支（包括远程）
git branch -a

# 查看分支详细信息
git branch -v
```

### 切换分支

```bash
git checkout branch-name
```

### 删除分支

```bash
# 删除本地分支（已合并）
git branch -d branch-name

# 强制删除本地分支（未合并）
git branch -D branch-name

# 删除远程分支
git push origin --delete branch-name
```

---

## 3. 代码提交

### 添加文件

```bash
# 添加指定文件
git add filename

# 添加所有修改
git add .

# 添加所有变化（包括删除）
git add -A
```

### 提交代码

```bash
# 提交（推荐使用规范格式）
git commit -m "feat: 新增功能描述"

# 提交并添加详细描述
git commit -m "fix: 修复bug" -m "详细描述：解决了xxx问题"

# 追加到上次提交（不推荐频繁使用）
git commit --amend
```

### 提交信息规范（Conventional Commits）

| 类型 | 说明 | 示例 |
|------|------|------|
| `feat` | 新增功能 | `feat: 添加用户登录功能` |
| `fix` | 修复bug | `fix: 修复页面布局问题` |
| `docs` | 文档更新 | `docs: 更新API文档` |
| `style` | 代码样式 | `style: 优化代码格式` |
| `refactor` | 代码重构 | `refactor: 重构数据库逻辑` |
| `test` | 测试相关 | `test: 添加单元测试` |
| `chore` | 构建/工具 | `chore: 更新依赖版本` |

---

## 4. 远程操作

### 拉取代码

```bash
# 拉取当前分支最新代码
git pull

# 拉取指定分支
git pull origin branch-name
```

### 推送代码

```bash
# 推送到远程分支（首次推送）
git push -u origin branch-name

# 推送到已关联分支
git push

# 强制推送（谨慎使用）
git push -f origin branch-name
```

### 查看远程仓库

```bash
# 查看远程仓库信息
git remote -v

# 添加远程仓库
git remote add origin git@github.com:username/repo.git

# 修改远程仓库地址
git remote set-url origin git@github.com:username/repo.git
```

---

## 5. 分支合并

### 合并分支到当前分支

```bash
# 切换到目标分支
git checkout main

# 合并指定分支
git merge feature-branch

# 合并时禁用快进模式（保留完整历史）
git merge --no-ff feature-branch
```

### 解决冲突

当合并时出现冲突，需要手动解决：

1. 打开冲突文件，找到 `<<<<<<<`、`=======`、`>>>>>>>` 标记
2. 手动编辑保留需要的代码
3. 添加解决后的文件：`git add filename`
4. 继续合并：`git commit`

### Rebase（变基）

```bash
# 将当前分支变基到目标分支
git rebase main

# 交互式变基（修改提交历史）
git rebase -i HEAD~3
```

---

## 6. 常见问题

### Q1: 撤销未提交的修改

```bash
# 撤销单个文件
git checkout -- filename

# 撤销所有未提交修改
git checkout .
```

### Q2: 回滚已提交的代码

```bash
# 查看提交历史
git log --oneline

# 回滚到指定版本（保留历史）
git revert commit-hash

# 强制回滚（丢弃后续提交，谨慎使用）
git reset --hard commit-hash
```

### Q3: 查看修改内容

```bash
# 查看未暂存的修改
git diff

# 查看已暂存的修改
git diff --cached

# 查看两次提交之间的差异
git diff commit1..commit2
```

### Q4: 设置默认分支

```bash
# 查看当前默认分支
git remote show origin | grep "HEAD branch"
```

> **注意**：修改远程默认分支需要在 GitHub 网页端设置。

---

## 📝 常用命令速查表

| 命令 | 说明 |
|------|------|
| `git status` | 查看工作区状态 |
| `git log` | 查看提交历史 |
| `git stash` | 暂存当前修改 |
| `git stash pop` | 恢复暂存的修改 |
| `git fetch` | 拉取远程更新（不合并） |
| `git tag` | 创建标签 |