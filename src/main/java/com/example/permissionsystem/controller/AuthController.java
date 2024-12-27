// src/main/java/com/example/permissionsystem/controller/AuthController.java
package com.example.permissionsystem.controller;

import com.example.permissionsystem.dto.AuthRequest;
import com.example.permissionsystem.dto.AuthResponse;
import com.example.permissionsystem.model.TUser;
import com.example.permissionsystem.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final OrganizationService organizationService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(OrganizationService organizationService, PasswordEncoder passwordEncoder) {
        this.organizationService = organizationService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 用户登录接口
     *
     * @param request 登录请求，包含用户名和密码
     * @return 登录响应，包含是否成功、消息和权限
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();

        // 查询用户
        List<TUser> users = organizationService.queryUsers("fName = '" + username + "'");
        if (users != null && !users.isEmpty()) {
            TUser user = users.get(0);
            // 使用 BCrypt 匹配密码
            if (passwordEncoder.matches(password, user.getFPassword())) {
                Set<String> permissions = organizationService.getUserPermissions(user.getFID());
                AuthResponse response = new AuthResponse(true, "登录成功", permissions);
                return ResponseEntity.ok(response);
            }
        }
        AuthResponse response = new AuthResponse(false, "用户名或密码错误", null);
        return ResponseEntity.status(401).body(response);
    }
}
