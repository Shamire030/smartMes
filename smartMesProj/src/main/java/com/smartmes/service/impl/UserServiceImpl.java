package com.smartmes.service.impl;

import com.smartmes.mapper.UserMapper;
import com.smartmes.model.User;
import com.smartmes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务实现类
 * 实现用户相关的业务逻辑
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }
    
    @Override
    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }
    
    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }
    
    @Override
    public boolean addUser(User user) {
        int result = userMapper.insert(user);
        return result > 0;
    }
    
    @Override
    public boolean updateUser(User user) {
        int result = userMapper.update(user);
        return result > 0;
    }
    
    @Override
    public boolean deleteUser(Long id) {
        int result = userMapper.deleteById(id);
        return result > 0;
    }
    
    @Override
    public boolean deleteUsers(List<Long> ids) {
        int result = userMapper.deleteBatch(ids);
        return result > 0;
    }
    
    @Override
    public User login(String username, String password) {
        // 根据用户名获取用户信息
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return null;
        }
        
        // 验证密码（实际项目中应该使用加密算法验证，这里简化为明文比较）
        if (password.equals(user.getPassword())) {
            return user;
        }
        
        return null;
    }

}