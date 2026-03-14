package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 禁用 CSRF（JWT 不需要）
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 先允许所有请求通过 Security，交给我们的 JWT 拦截器去处理
                )
                .formLogin(form -> form.disable()) // 禁用默认表单登录
                .httpBasic(basic -> basic.disable()); // 禁用你看到的那个弹窗（Basic Auth）

        return http.build();
    }
}
