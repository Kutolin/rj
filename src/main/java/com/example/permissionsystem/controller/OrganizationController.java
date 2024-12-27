// src/main/java/com/example/permissionsystem/controller/OrganizationController.java
package com.example.permissionsystem.controller;

import com.example.permissionsystem.dto.CreateOrganizationRequest;
import com.example.permissionsystem.dto.DeleteOrganizationRequest;
import com.example.permissionsystem.dto.UpdateOrganizationRequest;
import com.example.permissionsystem.model.TOrganization;
import com.example.permissionsystem.service.OrganizationService;
import com.example.permissionsystem.service.OrganizationTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    /**
     * 创建组织
     *
     * @param request 创建组织请求
     * @return 操作结果
     */
    @PostMapping("/create")
    public ResponseEntity<String> createOrganization(@RequestBody CreateOrganizationRequest request) {
        TOrganization organization = new TOrganization(
                request.getFID(),
                request.getFName(),
                request.getFHigherUpfIDs(),
                request.getFPermissions(),
                request.getFRemark(),
                request.getFOrgGUID()
        );
        boolean success = organizationService.addOrganization(organization);
        if (success) {
            return ResponseEntity.ok("组织创建成功");
        } else {
            return ResponseEntity.status(500).body("组织创建失败");
        }
    }

    /**
     * 更新组织
     *
     * @param request 更新组织请求
     * @return 操作结果
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateOrganization(@RequestBody UpdateOrganizationRequest request) {
        TOrganization organization = new TOrganization(
                request.getFID(),
                request.getFName(),
                request.getFHigherUpfIDs(),
                request.getFPermissions(),
                request.getFRemark(),
                request.getFOrgGUID()
        );
        boolean success = organizationService.updateOrganization(organization);
        if (success) {
            return ResponseEntity.ok("组织更新成功");
        } else {
            return ResponseEntity.status(500).body("组织更新失败");
        }
    }

    /**
     * 删除组织
     *
     * @param request 删除组织请求
     * @return 操作结果
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteOrganization(@RequestBody DeleteOrganizationRequest request) {
        boolean success = organizationService.deleteOrganization(request.getFID());
        if (success) {
            return ResponseEntity.ok("组织删除成功");
        } else {
            return ResponseEntity.status(500).body("组织删除失败");
        }
    }

    /**
     * 查询组织
     *
     * @param fID 组织ID（可选）
     * @return 组织列表
     */
    @GetMapping("/query")
    public ResponseEntity<List<TOrganization>> queryOrganizations(@RequestParam(required = false) String fID) {
        String whereClause = null;
        if (fID != null && !fID.trim().isEmpty()) {
            whereClause = "fID = '" + fID + "'";
        }
        List<TOrganization> organizations = organizationService.queryOrganizations(whereClause);
        return ResponseEntity.ok(organizations);
    }

    /**
     * 获取组织树
     *
     * @return 组织树列表
     */
    @GetMapping("/tree")
    public ResponseEntity<List<OrganizationTreeNode>> getOrganizationTree() {
        List<OrganizationTreeNode> tree = organizationService.loadOrganizationTree();
        return ResponseEntity.ok(tree);
    }
}
