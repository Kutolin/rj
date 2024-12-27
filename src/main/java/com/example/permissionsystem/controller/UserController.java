// src/main/java/com/example/permissionsystem/controller/UserController.java
package com.example.permissionsystem.controller;

import com.example.permissionsystem.dto.CreateUserRequest;
import com.example.permissionsystem.dto.DeleteUserRequest;
import com.example.permissionsystem.dto.UpdateUserRequest;
import com.example.permissionsystem.model.TUser;
import com.example.permissionsystem.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final OrganizationService organizationService;

    @Autowired
    public UserController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * 创建用户
     *
     * @param request 创建用户请求
     * @return 操作结果
     */
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest request) {
        TUser user = new TUser(
                request.getFID(),
                request.getFOrgIDs(),
                request.getFUserGUID(),
                request.getFName(),
                request.getFPassword(), // 密码将在 Service 层加密
                request.getFRemark()
        );
        boolean success = organizationService.addUser(user);
        if (success) {
            return ResponseEntity.ok("用户创建成功");
        } else {
            return ResponseEntity.status(500).body("用户创建失败");
        }
    }

    /**
     * 更新用户
     *
     * @param request 更新用户请求
     * @return 操作结果
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserRequest request) {
        TUser user = new TUser(
                request.getFID(),
                request.getFOrgIDs(),
                request.getFUserGUID(),
                request.getFName(),
                request.getFPassword(),
                request.getFRemark()
        );
        boolean success = organizationService.updateUser(user);
        if (success) {
            return ResponseEntity.ok("用户更新成功");
        } else {
            return ResponseEntity.status(500).body("用户更新失败");
        }
    }

    /**
     * 删除用户
     *
     * @param request 删除用户请求
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody DeleteUserRequest request) {
        boolean success = organizationService.deleteUser(request.getFID());
        if (success) {
            return ResponseEntity.ok("用户删除成功");
        } else {
            return ResponseEntity.status(500).body("用户删除失败");
        }
    }

    /**
     * 查询用户
     *
     * @param fID 用户ID（可选）
     * @return 用户列表
     */
    @GetMapping("/query")
    public ResponseEntity<List<TUser>> queryUsers(@RequestParam(required = false) Integer fID) {
        String whereClause = null;
        if (fID != null) {
            whereClause = "fID = " + fID;
        }
        List<TUser> users = organizationService.queryUsers(whereClause);
        return ResponseEntity.ok(users);
    }
}
