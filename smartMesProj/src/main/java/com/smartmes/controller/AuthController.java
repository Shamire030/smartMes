package com.smartmes.controller;

import com.smartmes.model.User;
import com.smartmes.service.UserService;
import com.smartmes.util.Result;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 * 提供登录、登出、获取用户信息和刷新token等接口
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    // JWT配置（实际项目中应该放在配置文件中）
    private static final String SECRET_KEY = "smartmes_secret_key";
    private static final long EXPIRATION_TIME = 86400000; // 24小时
    
    /**
     * 用户登录接口
     * @param loginData 包含用户名和密码的登录数据
     * @return 登录结果，包含token和用户信息
     */
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        
        // 验证用户名和密码
        User user = userService.login(username, password);
        if (user == null) {
            return Result.fail(401, "用户名或密码错误");
        }
        
        // 生成JWT token
        String token = generateToken(user);
        
        // 构建响应数据
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);
        
        return Result.success(response);
    }
    
    /**
     * 用户登出接口
     * @return 登出结果
     */
    @PostMapping("/logout")
    public Result logout() {
        // 这里可以添加登出逻辑，如清除token缓存等
        return Result.success("登出成功");
    }
    
    /**
     * 获取当前用户信息接口
     * @return 用户信息
     */
    @GetMapping("/user-info")
    public Result getUserInfo(@RequestHeader("Authorization") String token) {
        // 解析token获取用户名
        String username = parseToken(token);
        if (username == null) {
            return Result.fail(401, "无效的token");
        }
        
        // 获取用户信息
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return Result.fail(404, "用户不存在");
        }
        
        return Result.success(user);
    }
    
    /**
     * 刷新token接口
     * @return 新的token
     */
    @PostMapping("/refresh-token")
    public Result refreshToken(@RequestHeader("Authorization") String token) {
        // 解析token获取用户名
        String username = parseToken(token);
        if (username == null) {
            return Result.fail(401, "无效的token");
        }
        
        // 获取用户信息
        User user = userService.getUserByUsername(username);
        if (user == null) {
            return Result.fail(404, "用户不存在");
        }
        
        // 生成新的token
        String newToken = generateToken(user);
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", newToken);
        
        return Result.success(response);
    }
    
    /**
     * 生成JWT token
     * @param user 用户信息
     * @return token字符串
     */
    private String generateToken(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
        
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .claim("role", user.getRole())
                .claim("userId", user.getId())
                .compact();
    }
    
    /**
     * 解析JWT token获取用户名
     * @param token token字符串
     * @return 用户名
     */
    private String parseToken(String token) {
        try {
            // 去除"Bearer "前缀
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}