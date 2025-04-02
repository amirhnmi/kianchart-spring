package com.kianchart.kianchart.api.permission.rolePermissions;

import com.kianchart.kianchart.core.enums.SortDirection;
import com.kianchart.kianchart.core.response.CustomResponseEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class RolePermissionController {
    private final RolePermissionService rolePermissionService;

    @Autowired
    public RolePermissionController(RolePermissionService rolePermissionService){
        this.rolePermissionService = rolePermissionService;
    }

    @GetMapping(value = "/role-permission/list")
    public CustomResponseEntity<List<RolePermissionDTO.Response>> getAllRolePermission(
            @RequestParam(defaultValue = "asc") SortDirection sort,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "false") boolean total
    ){
        List<RolePermissionDTO.Response> rolePermissions = rolePermissionService.getAllRolePermission(sort,skip,limit);
        Long totalCount = total ? rolePermissionService.countAllRolePermission() : null;
        return CustomResponseEntity.showList(rolePermissions,totalCount);
    }

    @PostMapping(value = "/role-permission/create")
    public CustomResponseEntity<RolePermissionDTO.Response> createRolePermission(
            @Valid @RequestBody RolePermissionDTO.CreateRolePermissionRequest createRequest
    ){
        RolePermissionDTO.Response rolePermission = rolePermissionService.createRolePermission(createRequest);
        return CustomResponseEntity.showDetail(rolePermission);
    }

    @DeleteMapping(value = "/role-permission/{id}/delete")
    public ResponseEntity<String> deleteRolePermission(@PathVariable Long id){
        rolePermissionService.deleteRolePermission(id);
        return ResponseEntity.status(HttpStatus.OK).body("data successfully deleted");
    }
}
