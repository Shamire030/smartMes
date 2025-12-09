package com.smartmes.controller;

import com.smartmes.model.User;
import com.smartmes.service.UserService;
import com.smartmes.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户控制器
 * 提供用户相关的REST API接口
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 根据ID获取用户
     */
    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user != null ? Result.success(user) : Result.fail(404, "用户不存在");
    }
    
    /**
     * 获取所有用户
     */
    @GetMapping
    public Result<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return Result.success(users);
    }
    
    /**
     * 创建新用户
     */
    @PostMapping
    public Result<User> createUser(@RequestBody User user) {
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        boolean success = userService.addUser(user);
        return success ? Result.success(user) : Result.fail("创建用户失败");
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/{id}")
    public Result<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        // 先检查用户是否存在
        if (userService.getUserById(id) == null) {
            return Result.fail(404, "用户不存在");
        }
        user.setId(id);
        user.setUpdateTime(LocalDateTime.now());
        boolean success = userService.updateUser(user);
        return success ? Result.success(user) : Result.fail("更新用户失败");
    }
    
    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        boolean success = userService.deleteUser(id);
        return success ? Result.success() : Result.fail(404, "删除失败，用户不存在");
    }
    
    /**
     * 批量删除用户
     */
    @DeleteMapping
    public Result<Void> deleteUsers(@RequestBody List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Result.fail("请选择要删除的用户");
        }
        boolean success = userService.deleteUsers(ids);
        return success ? Result.success() : Result.fail("批量删除失败");
    }

}