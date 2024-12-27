// src/main/java/com/example/permissionsystem/config/SecurityConfig.java
package com.example.permissionsystem.config;

import com.example.permissionsystem.filter.RateLimitingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final RateLimitingFilter rateLimitingFilter;

    @Autowired
    public SecurityConfig(RateLimitingFilter rateLimitingFilter) {
        this.rateLimitingFilter = rateLimitingFilter;
    }

    /**
     * 定义密码编码器，使用 BCrypt
     *
     * @return PasswordEncoder实例
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 定义安全过滤链
     *
     * @param http HttpSecurity对象
     * @return SecurityFilterChain实例
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 在生产环境中应启用 CSRF 保护
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll() // 允许未认证用户访问登录接口
                        .anyRequest().authenticated() // 其他接口需要认证
                )
                .httpBasic(); // 使用 HTTP Basic 认证

        // 添加限流过滤器
        http.addFilterBefore(rateLimitingFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
