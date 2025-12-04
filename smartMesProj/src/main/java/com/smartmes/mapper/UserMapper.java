package com.smartmes.mapper;

import com.smartmes.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper接口
 * 定义用户相关的数据库操作
 */
@Mapper
public interface UserMapper {
    
    /**
     * 根据ID查询用户
     * @param id 用户ID
     * @return 用户对象
     */
    User selectById(@Param("id") Long id);
    
    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户对象
     */
    User selectByUsername(@Param("username") String username);
    
    /**
     * 查询所有用户列表
     * @return 用户列表
     */
    List<User> selectAll();
    
    /**
     * 插入用户
     * @param user 用户对象
     * @return 影响的行数
     */
    int insert(User user);
    
    /**
     * 更新用户信息
     * @param user 用户对象
     * @return 影响的行数
     */
    int update(User user);
    
    /**
     * 根据ID删除用户
     * @param id 用户ID
     * @return 影响的行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除用户
     * @param ids 用户ID列表
     * @return 影响的行数
     */
    int deleteBatch(@Param("ids") List<Long> ids);

}