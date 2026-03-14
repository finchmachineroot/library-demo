package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private BCryptPasswordEncoder encoder;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<String> login(@RequestBody User loginUser) {
        log.info(">>> 用户尝试登录: {}", loginUser.getUsername());

        // 1. 根据用户名查找用户
        User user = userRepository.findByUsername(loginUser.getUsername());

        // 2. 校验密码（实际企业开发需对比加密后的散列值）
        if (user != null && encoder.matches(loginUser.getPassword(), user.getPassword())) {
            // 3. 登录成功，生成 Token
            String token = jwtUtils.createToken(user.getId(), user.getUsername());
            log.info("<<< 登录成功，生成 Token: {}", token);
            return Result.success(token);
        }

        log.warn("<<< 登录失败，用户名或密码错误");
        return Result.error("用户名或密码错误");
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Result<String> register(@RequestBody User user) {
        // 1. 检查用户名是否已存在
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return Result.error("用户名已存在");
        }

        // 2. 关键：将明文密码加密后再存储
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // 3. 保存到数据库
        userRepository.save(user);
        return Result.success("注册成功");
    }
}
