package com.kianchart.kianchart.controller;

import com.kianchart.kianchart.model.PermissionModel;
import com.kianchart.kianchart.service.PermissionService;
import com.kianchart.kianchart.enums.SortDirection;
import com.kianchart.kianchart.utils.CustomResponseEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PermissionController {
    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping(value = "/permission/list")
    public CustomResponseEntity<List<PermissionModel.Response>> getAllPermission(
            @RequestParam(defaultValue = "asc") SortDirection sort,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "false") boolean total
    ) {
        List<PermissionModel.Response> permissions = permissionService.getAllPermission(sort, skip, limit);
        Long totalCount = total ? permissionService.countAllPermissions() : null;
        return CustomResponseEntity.showList(permissions, totalCount);
    }

    @PostMapping(value = "/permission/{id}/show")
    public CustomResponseEntity<PermissionModel.Response> getOnePermission(@PathVariable Long id) {
        PermissionModel.Response permission = permissionService.getOnePermission(id);
        return CustomResponseEntity.showDetail(permission);
    }

    @PostMapping(value = "/permission/create")
    public CustomResponseEntity<PermissionModel.Response> createPermission(
            @Valid @RequestBody PermissionModel.CreatePermissionRequest createRequest
    ) {
        PermissionModel.Response permission = permissionService.createPermission(createRequest);
        return CustomResponseEntity.showDetail(permission);
    }

    @PostMapping(value = "/permission/{id}/update")
    public CustomResponseEntity<PermissionModel.Response> UpdatePermission(
            @PathVariable Long id,
            @Valid @RequestBody PermissionModel.UpdatePermissionRequest updateRequest
    ) {
        PermissionModel.Response permission = permissionService.updatePermission(id, updateRequest);
        return CustomResponseEntity.showDetail(permission);
    }

    @PostMapping(value = "/permission/{id}/delete")
    public ResponseEntity<String> deletePermission(@PathVariable Long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.status(HttpStatus.OK).body("data successfully deleted");
    }
}
