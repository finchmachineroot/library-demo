package com.example.demo.interceptor;

import com.example.demo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 如果不是映射到方法（比如访问的是静态资源），直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 2. 从请求头中获取 Token
        // 按照规范，通常放在 Authorization 字段，格式为: Bearer <token>
        String authHeader = request.getHeader("Authorization");
        log.info(">>> 拦截器启动，检查 Token: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("<<< Token 为空或格式错误");
            response.setStatus(401); // 未授权
            return false;
        }

        // 3. 截取真正的 Token 字符串
        String token = authHeader.substring(7);

        try {
            // 4. 解析 Token
            Claims claims = jwtUtils.parseToken(token);
            log.info("<<< Token 校验通过，当前用户: {}", claims.getSubject());
            return true;
        } catch (Exception e) {
            log.error("<<< Token 校验失败: {}", e.getMessage());
            response.setStatus(401);
            return false;
        }
    }
}
