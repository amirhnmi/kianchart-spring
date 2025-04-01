package com.kianchart.kianchart.api.permission.permissions;

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
public class PermissionController {
    private final PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RequestMapping(value = "/permission/list", method = RequestMethod.GET)
    public CustomResponseEntity<List<PermissionDTO.Response>> getAllPermission(
            @RequestParam(defaultValue = "asc") SortDirection sort,
            @RequestParam(defaultValue = "0") int skip,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "false") boolean total
    ) {
        List<PermissionDTO.Response> permissions = permissionService.getAllPermission(sort, skip, limit);
        Long totalCount = total ? permissionService.countAllPermissions() : null;
        return CustomResponseEntity.showList(permissions, totalCount);
    }

    @RequestMapping(value = "/permission/{id}/show", method = RequestMethod.GET)
    public CustomResponseEntity<PermissionDTO.Response> getOnePermission(@PathVariable Long id){
        PermissionDTO.Response permission = permissionService.getOnePermission(id);
        return CustomResponseEntity.showDetail(permission);
    }

    @RequestMapping(value = "/permission/create", method = RequestMethod.POST)
    public CustomResponseEntity<PermissionDTO.Response> createPermission(
            @Valid @RequestBody PermissionDTO.CreatePermissionRequest createRequest
    ){
        PermissionDTO.Response permission = permissionService.createPermission(createRequest);
        return CustomResponseEntity.showDetail(permission);
    }

    @RequestMapping(value = "/permission/{id}/update", method = RequestMethod.PUT)
    public CustomResponseEntity<PermissionDTO.Response> UpdatePermission(
            @PathVariable Long id,
            @Valid @RequestBody PermissionDTO.UpdatePermissionRequest updateRequest
    ){
        PermissionDTO.Response permission = permissionService.updatePermission(id,updateRequest);
        return CustomResponseEntity.showDetail(permission);
    }

    @RequestMapping(value = "/permission/{id}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<String> deletePermission(@PathVariable Long id){
        permissionService.deletePermission(id);
        return ResponseEntity.status(HttpStatus.OK).body("data successfully deleted");
    }
}
