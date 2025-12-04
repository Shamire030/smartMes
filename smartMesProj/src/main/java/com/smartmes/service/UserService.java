package com.smartmes.service;

import com.smartmes.model.User;

import java.util.List;

/**
 * 用户服务接口
 * 定义用户相关的业务逻辑
 */
public interface UserService {
    
    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户对象
     */
    User getUserById(Long id);
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象
     */
    User getUserByUsername(String username);
    
    /**
     * 获取所有用户列表
     * @return 用户列表
     */
    List<User> getAllUsers();
    
    /**
     * 新增用户
     * @param user 用户对象
     * @return 是否成功
     */
    boolean addUser(User user);
    
    /**
     * 更新用户信息
     * @param user 用户对象
     * @return 是否成功
     */
    boolean updateUser(User user);
    
    /**
     * 删除用户
     * @param id 用户ID
     * @return 是否成功
     */
    boolean deleteUser(Long id);
    
    /**
     * 批量删除用户
     * @param ids 用户ID列表
     * @return 是否成功
     */
    boolean deleteUsers(List<Long> ids);

}