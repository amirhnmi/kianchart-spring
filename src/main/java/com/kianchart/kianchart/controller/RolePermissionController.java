package com.kianchart.kianchart.controller;

import com.kianchart.kianchart.model.RolePermissionModel;
import com.kianchart.kianchart.service.RolePermissionService;
import com.kianchart.kianchart.enums.SortDirection;
import com.kianchart.kianchart.utils.CustomResponseEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class RolePermissionController {
    private final RolePermissionService rolePermissionService;

    @Autowired
    public RolePermissionController(RolePermissionService rolePermissionService){
        this.rolePermissionService = rolePermissionService;
    }

    @PostMapping(value = "/role-permission/list")
    public CustomResponseEntity<List<RolePermissionModel.Response>> getAllRolePermission(
            @RequestParam(defaultValue = "asc") SortDirection sort,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "false") boolean total
    ){
        List<RolePermissionModel.Response> rolePermissions = rolePermissionService.getAllRolePermission(sort,skip,limit);
        Long totalCount = total ? rolePermissionService.countAllRolePermission() : null;
        return CustomResponseEntity.showList(rolePermissions,totalCount);
    }

    @PostMapping(value = "/role-permission/create")
    public ResponseEntity<Map<String, String>> createRolePermission(
            @Valid @RequestBody RolePermissionModel.CreateRolePermissionRequest createRequest
    ){
        Map<String,String> response = rolePermissionService.createRolePermission(createRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(value = "/role-permission/{id}/delete")
    public ResponseEntity<String> deleteRolePermission(@PathVariable Long id){
        rolePermissionService.deleteRolePermission(id);
        return ResponseEntity.status(HttpStatus.OK).body("data successfully deleted");
    }
}
